package github.ant.mxsm.netty.socket.server;

import org.junit.Test;

import github.ant.mxsm.netty.Heartbeat;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class SocketStarterTest {

	@Test
	public void testStart() throws Exception{
		
		 EventLoopGroup workerGroup = new NioEventLoopGroup();
		 Bootstrap b = new Bootstrap();
		 b.group(workerGroup);
		 b.channel(NioSocketChannel.class);
		 b.option(ChannelOption.SO_KEEPALIVE, true);
		 b.handler(new ChannelInitializer<SocketChannel>() {
             @Override
             public void initChannel(SocketChannel ch) throws Exception {
            	 ch.pipeline().addLast(new EEEEE())
            	 //.addLast(new ProtobufVarint32LengthFieldPrepender())
                 .addLast(new ProtobufEncoder());
                 
                 //ch.pipeline().addLast(new ProtoBufClientHandler());
             }
         });
		 ChannelFuture f = b.connect("127.0.0.1", 9980).sync();
		 f.channel().writeAndFlush(Unpooled.copiedBuffer(Heartbeat.HEARTBEAT));
		 f.channel().closeFuture().sync();
	}

	class EEEEE extends ChannelDuplexHandler{
		
	}
	
}
