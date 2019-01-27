package github.ant.mxsm.netty.socket.server;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import github.ant.mxsm.netty.Heartbeat;
import github.ant.mxsm.protocol.protobuf.Message;
import github.ant.mxsm.protocol.protobuf.Message.MessageProtobuf;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
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

public class SocketStarterTest {

	@Test
	public void testStart() throws Exception {

		EventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workerGroup);
		b.channel(NioSocketChannel.class);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline()
						.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS)).addLast(new HearBeatHandler())
						.addLast(new ProtobufVarint32FrameDecoder())
						.addLast("protobufFrameDecoder",
								new ProtobufDecoder(Message.MessageProtobuf.getDefaultInstance()))
						.addLast(new ProtobufVarint32LengthFieldPrepender1()).addLast(new ProtobufEncoder())
						.addLast(new ReponseHandler());
				// ch.pipeline().addLast(new ProtoBufClientHandler());
			}
		});
		ChannelFuture f = b.connect("127.0.0.1", 9980).sync();
		/*
		 * for(int i = 0; i < 10;++i) {
		 * f.channel().writeAndFlush(Unpooled.copiedBuffer(Heartbeat.HEARTBEAT)); }
		 */
		/*
		 * for(int i = 0; i < 100;++i) {
		 * f.channel().writeAndFlush(MessageProtobuf.newBuilder().setCtrlMessageId(i+"")
		 * .build()); }
		 */
		//f.channel().writeAndFlush(MessageProtobuf.newBuilder().setCtrlMessageId("uuuu").build());
		//f.channel().closeFuture().sync();
	}

	class ProtobufVarint32LengthFieldPrepender1 extends ProtobufVarint32LengthFieldPrepender {

		@Override
		protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
			msg.markReaderIndex();
			int length = msg.readableBytes();
			if (length < Heartbeat.HEARTBEAT.length) {
				super.encode(ctx, msg, out);
				return;
			}
			byte[] readHeartBeat = new byte[Heartbeat.HEARTBEAT.length];
			msg.readBytes(readHeartBeat);
			if (Arrays.equals(Heartbeat.HEARTBEAT, readHeartBeat)) {
				ByteBuf encode = ctx.alloc().buffer();
				out.writeBytes(encode.writeBytes(Heartbeat.HEARTBEAT));
				return;
			}
			msg.resetReaderIndex();
			super.encode(ctx, msg, out);
		}

	}

	class HearBeatHandler extends ChannelDuplexHandler {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			if (msg instanceof ByteBuf) {
				ByteBuf buf = (ByteBuf) msg;

				buf.markReaderIndex();
				int length = buf.readableBytes();
				if (length < Heartbeat.HEARTBEAT.length) {
					return;
				}
				byte[] readHeartBeat = new byte[Heartbeat.HEARTBEAT.length];
				buf.readBytes(readHeartBeat);
				if (Arrays.equals(Heartbeat.HEARTBEAT, readHeartBeat)) {
					System.out.println("Recive Heart beat ........");
					System.out.println(ctx.channel().isActive());
					if(ctx.channel().isActive()) {
						channelReadComplete(ctx);
					}
					return;
				}
				buf.resetReaderIndex();
			}
			super.channelRead(ctx, msg);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			super.channelReadComplete(ctx);
		}

	}

	class ReponseHandler extends ChannelDuplexHandler {

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			System.out.println(1111);
		}
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			MessageProtobuf message = (MessageProtobuf) msg;
			//System.out.println(message.getCtrlMessageId());
		}

		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			// TODO Auto-generated method stub
			if (evt instanceof IdleStateEvent) {
				IdleStateEvent et = (IdleStateEvent) evt;
				switch (et.state()) {
				case READER_IDLE:
					ctx.writeAndFlush(Unpooled.copiedBuffer(Heartbeat.HEARTBEAT));
					break;

				default:
					break;
				}
			} else {
				super.userEventTriggered(ctx, evt);
			}

		}
	}

}
