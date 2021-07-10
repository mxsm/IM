package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.common.Pair;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.ProtobufUtils;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.ChannelEventListener;
import com.github.mxsm.remoting.RemotingResponseCallback;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.ResponseCode;
import com.github.mxsm.remoting.common.Worker;
import com.github.mxsm.remoting.event.NettyEvent;
import com.github.mxsm.remoting.event.NettyEventPublisher;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import com.github.mxsm.remoting.netty.RequestTask;
import com.github.mxsm.remoting.netty.RequestTaskWrapper;
import com.github.mxsm.remoting.netty.ResponseFuture;
import io.netty.channel.ChannelHandlerContext;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since 0.1
 */
public abstract class NettyRemotingHandler extends AbstractNettyRemoting implements RemotingHandler,
    NettyEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRemotingHandler.class);

    private final Map<Integer /*request code*/, Pair<NettyRequestProcessor, ExecutorService>> processorTable = new HashMap<>(
        64);

    private Pair<NettyRequestProcessor, ExecutorService> defaultRequestProcessor;

    protected final NettyEventWorker nettyEventWorker = new NettyEventWorker();

    public NettyRemotingHandler(int permitsOneway, int permitsAsync) {
        super(permitsOneway, permitsAsync);
    }

    @Override
    public void processRequestCommand(final ChannelHandlerContext ctx, final RemotingCommand cmd) {

        final Pair<NettyRequestProcessor, ExecutorService> matchedPair = processorTable.get(cmd.getCode());
        final Pair<NettyRequestProcessor, ExecutorService> pair =
            null == matchedPair ? this.defaultRequestProcessor : matchedPair;

        final long commandId = cmd.getCommandId();

        if (pair == null) {
            String error = " request type " + cmd.getCode() + " not supported";
            final RemotingCommand response = RemotingCommandBuilder.buildRequestCommand().setCommandId(commandId)
                .setCode(ResponseCode.REQUEST_CODE_NOT_SUPPORTED).setResultMessage(error).build();
            ctx.writeAndFlush(response);
            LOGGER.error(NetUtils.parseChannelRemoteAddress(ctx.channel()) + error);
            return;
        }

        RequestTask requestTask = () -> {
            try {
                final RemotingResponseCallback callback = response -> {

                    if (ProtobufUtils.isOnewayRequest(cmd) || response == null) {
                        return;
                    }
                    try {
                        ctx.writeAndFlush(response);
                    } catch (Exception e) {
                        LOGGER.error("process request over, but response failed", e);
                    }
                };

                //异步处理
                NettyRequestProcessor requestProcessor = pair.getLeft();
                if (requestProcessor instanceof AsyncNettyRequestProcessor) {
                    AsyncNettyRequestProcessor processor = (AsyncNettyRequestProcessor) requestProcessor;
                    processor.asyncProcessRequest(ctx, cmd, callback);
                } else if (requestProcessor instanceof NettyRequestProcessor) {
                    RemotingCommand response = requestProcessor.processRequest(ctx, cmd);
                    callback.callback(response);
                } else {
                    //
                }
            } catch (Exception e) {
                LOGGER.error("process request exception", e);
                //处理返回数据
                if (!ProtobufUtils.isOnewayRequest(cmd)) {
                    final RemotingCommand response = RemotingCommandBuilder.buildRequestCommand()
                        .setCommandId(commandId)
                        .setCode(ResponseCode.SYSTEM_ERROR).setResultMessage(e.getMessage()).build();
                    ctx.writeAndFlush(response);
                }
            }
        };

        if (pair.getLeft().rejectRequest()) {
            final RemotingCommand response = RemotingCommandBuilder.buildRequestCommand().setCommandId(commandId)
                .setCode(ResponseCode.SYSTEM_BUSY).setResultMessage("request was rejected").build();
            ctx.writeAndFlush(response);
            return;
        }

        try {
            RequestTaskWrapper requestTaskWrapper = new RequestTaskWrapper(requestTask, ctx.channel(), cmd);
            pair.getRight().submit(requestTaskWrapper);
        } catch (Exception e) {
            if (!ProtobufUtils.isOnewayRequest(cmd)) {
                final RemotingCommand response = RemotingCommandBuilder.buildRequestCommand().setCommandId(commandId)
                    .setCode(ResponseCode.SYSTEM_BUSY).setResultMessage("submit RequestTaskWrapper to exe error")
                    .build();
                ctx.writeAndFlush(response);
            }
        }

    }

    @Override
    public void processResponseCommand(ChannelHandlerContext ctx, RemotingCommand cmd) {
        final long commandId = cmd.getCommandId();
        final ResponseFuture responseFuture = responseTable.get(commandId);
        if (responseFuture != null) {
            responseFuture.setResponseCommand(cmd);

            responseTable.remove(commandId);

            if (responseFuture.getInvokeCallback() != null) {
                executeInvokeCallback(responseFuture);
            } else {
                //同步发送InvokeCallback为空
                responseFuture.putRemotingCommandResponse(cmd);
                responseFuture.release();
            }
        } else {
            LOGGER.warn(
                "receive response, but not matched any request, " + NetUtils.parseChannelRemoteAddress(ctx.channel()));
            LOGGER.warn(cmd.toString());
        }
    }

    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor) {
        Pair<NettyRequestProcessor, ExecutorService> pair = Pair.builder(processor, executor);
        processorTable.put(requestCode, pair);
    }


    public void registerDefaultProcessor(NettyRequestProcessor processor, ExecutorService executor) {
        this.defaultRequestProcessor = Pair.builder(processor, executor);
    }

    public abstract ChannelEventListener getChannelEventListener();

    protected void putNettyEvent(final NettyEvent event) {
        this.nettyEventWorker.addNettyEvent(event);
    }

    public class NettyEventWorker extends Worker {

        private final Logger WLOGGER = LoggerFactory.getLogger(NettyEventWorker.class);

        private static final int MAX_SIZE = 10 * 10000;

        private final LinkedBlockingQueue<NettyEvent> nettyEventQueue = new LinkedBlockingQueue<>(MAX_SIZE);

        public void addNettyEvent(NettyEvent nettyEvent) {
            try {
                nettyEventQueue.add(nettyEvent);
            } catch (Exception e) {
                WLOGGER.warn("Netty event Queue is full[MAX_SIZE={}], drop this netty event[{}]", MAX_SIZE, nettyEvent);
            }
        }

        @Override
        public String getWorkerName() {
            return "NettyEventWork";
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used to create a thread, starting the thread
         * causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {

            final ChannelEventListener channelEventListener = NettyRemotingHandler.this.getChannelEventListener();

            while (isRunning()) {
                try {
                    NettyEvent nettyEvent = nettyEventQueue.poll(3000, TimeUnit.MILLISECONDS);
                    if (nettyEvent == null || channelEventListener == null) {
                        continue;
                    }
                    switch (nettyEvent.getEventType()) {
                        case IDLE:
                            break;
                        case CLOSE:
                            break;
                        case CONNECT:
                            break;
                        case EXCEPTION:
                            channelEventListener
                                .onExceptionCaught(nettyEvent.getChannel(), (Throwable) nettyEvent.getSource());
                            break;
                        default:
                    }
                } catch (Exception e) {
                    LOGGER.warn("NettyEventWork run exception", e);
                }
            }
        }
    }
}
