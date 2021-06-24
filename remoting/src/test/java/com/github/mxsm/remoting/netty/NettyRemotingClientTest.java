package com.github.mxsm.remoting.netty;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.protobuf.RemotingCommandType;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since
 */
class NettyRemotingClientTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void updateRegisterAddressList() {
    }

    @Test
    void invokeSync() {
    }

    @Test
    void invokeAsync() {
    }

    @Test
    void invokeOneway() {

        NettyRemotingClient client = new NettyRemotingClient(new NettyClientConfig());
        client.start();
       // RemotingCommand build = ;

        try {
            client.invokeOneway(null, null, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingConnectException e) {
            e.printStackTrace();
        } catch (RemotingTooMuchRequestException e) {
            e.printStackTrace();
        } catch (RemotingTimeoutException e) {
            e.printStackTrace();
        } catch (RemotingSendRequestException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*        RemotingCommand build = RemotingCommand.newBuilder().setCommandType(RemotingCommandType.CONNECT).build();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                    .addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                    .addLast(new ProtobufVarint32FrameDecoder())
                    .addLast("protobufFrameDecoder", new ProtobufDecoder(RemotingCommand.getDefaultInstance()))
                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                    .addLast(new ProtobufEncoder())
                    .addLast(new NettyClientHandler());
            }
        });
        try {
            ChannelFuture f = b.connect("127.0.0.1", 9998).sync();
            for (int i = 0; i < 100; ++i) {
                f.channel()
                    .writeAndFlush(RemotingCommand.newBuilder().setCommandType(RemotingCommandType.CONNECT).build());
            }
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    void start() {
    }

    @Test
    void shutdown() {
    }


}