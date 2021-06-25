package com.github.mxsm.remoting.netty;

import com.github.mxsm.common.Pair;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since 0.1
 */
public class NettyRemoting {

    private final Map<Integer /*request code*/, Pair<NettyRequestProcessor, ExecutorService>> processorTable = new HashMap<>(
        64);

    private Pair<NettyRequestProcessor, ExecutorService> defaultRequestProcessor;


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

    protected void processRequestCommand(final ChannelHandlerContext ctx, final RemotingCommand msg) {

        final Pair<NettyRequestProcessor, ExecutorService> matchedPair = processorTable.get(msg.getRequestCode());
        final Pair<NettyRequestProcessor, ExecutorService> pair = null == matchedPair ? this.defaultRequestProcessor : matchedPair;

        if(pair == null){

            return;
        }

        TaskInner taskInner = new TaskInner() {
            @Override
            public void run() {

            }
        };


    }

    protected void processResponseCommand(ChannelHandlerContext ctx, RemotingCommand cmd) {

    }

    protected  void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor){
        Pair<NettyRequestProcessor,ExecutorService> pair = Pair.builder(processor, executor);
        processorTable.put(requestCode,pair);
    }


    public void registerDefaultProcessor(NettyRequestProcessor processor, ExecutorService executor) {
        this.defaultRequestProcessor = Pair.builder(processor, executor);
    }
}
