package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.common.Pair;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.ProtobufUtils;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.RemotingResponseCallback;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import com.github.mxsm.remoting.netty.RequestTask;
import com.github.mxsm.remoting.netty.RequestTaskWrapper;
import com.github.mxsm.remoting.netty.ResponseFuture;
import io.netty.channel.ChannelHandlerContext;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since 0.1
 */
public class NettyRemotingHandler extends AbstractNettyRemoting {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRemotingHandler.class);

    private final Map<Integer /*request code*/, Pair<NettyRequestProcessor, ExecutorService>> processorTable = new HashMap<>(
        64);

    private Pair<NettyRequestProcessor, ExecutorService> defaultRequestProcessor;

    public NettyRemotingHandler(int permitsOneway, int permitsAsync) {
        super(permitsOneway, permitsAsync);
    }


    public void processMessageReceived(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
        final RemotingCommand cmd = msg;
        switch (cmd.getCommandType()) {
            case REQUEST:
                processRequestCommand(ctx, msg);
                break;
            case RESPONSE:
                processResponseCommand(ctx, msg);
                break;
            default:
                break;
        }
    }

    protected void processRequestCommand(final ChannelHandlerContext ctx, final RemotingCommand cmd) {

        final Pair<NettyRequestProcessor, ExecutorService> matchedPair = processorTable.get(cmd.getRequestCode());
        final Pair<NettyRequestProcessor, ExecutorService> pair = null == matchedPair ? this.defaultRequestProcessor : matchedPair;

        if(pair == null){
            //TODO build RemotingCommand for response
            String error = " request type " + cmd.getRequestCode() + " not supported";
            final RemotingCommand response = RemotingCommandBuilder.buildRequestCommand();
            ctx.writeAndFlush(response);
            LOGGER.error(NetUtils.parseChannelRemoteAddr(ctx.channel()) + error);
            return;
        }

        RequestTask requestTask = () -> {
            try {
                final RemotingResponseCallback callback = response -> {

                    if(ProtobufUtils.isOnewayRequest(cmd) || response == null){
                        return;
                    }
                    try {
                        ctx.writeAndFlush(response);
                    } catch (Exception e) {
                        LOGGER.error("process request over, but response failed", e);
                        LOGGER.error(cmd.toString());
                        LOGGER.error(response.toString());
                    }

                };

                if(pair.getLeft() instanceof AsyncNettyRequestProcessor){
                    AsyncNettyRequestProcessor processor = (AsyncNettyRequestProcessor)pair.getLeft();
                    processor.asyncProcessRequest(ctx, cmd, callback);
                }else{
                    NettyRequestProcessor processor = pair.getLeft();
                    RemotingCommand response = processor.processRequest(ctx, cmd);
                    callback.callback(response);
                }
            } catch (Exception e) {
                LOGGER.error("process request exception", e);
                LOGGER.error(cmd.toString());
                //处理返回数据
                if (!ProtobufUtils.isOnewayRequest(cmd)) {
                    //TODO
                    final RemotingCommand response = RemotingCommandBuilder.buildResponseCommand();
                    ctx.writeAndFlush(response);
                }
            }
        };

        if(pair.getLeft().rejectRequest()){
            //TODO
            final RemotingCommand response = RemotingCommandBuilder.buildResponseCommand();
            ctx.writeAndFlush(response);
            return;
        }

        try {
            RequestTaskWrapper requestTaskWrapper = new RequestTaskWrapper(requestTask, ctx.channel(), cmd);
            pair.getRight().submit(requestTaskWrapper);
        } catch (Exception e) {
            if (!ProtobufUtils.isOnewayRequest(cmd)) {
                //TODO
                final RemotingCommand response = RemotingCommandBuilder.buildResponseCommand();
                ctx.writeAndFlush(response);
            }
        }

    }

    protected void processResponseCommand(ChannelHandlerContext ctx, RemotingCommand cmd) {
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
            LOGGER.warn("receive response, but not matched any request, " + NetUtils.parseChannelRemoteAddr(ctx.channel()));
            LOGGER.warn(cmd.toString());
        }
    }

    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor){
        Pair<NettyRequestProcessor,ExecutorService> pair = Pair.builder(processor, executor);
        processorTable.put(requestCode,pair);
    }


    public void registerDefaultProcessor(NettyRequestProcessor processor, ExecutorService executor) {
        this.defaultRequestProcessor = Pair.builder(processor, executor);
    }

    @Override
    public ExecutorService getCallbackExecutor() {
        return null;
    }
}
