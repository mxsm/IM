package github.ant.mxsm.netty.channel.handler;

import java.util.Arrays;
import java.util.List;

import io.netty.channel.Channel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.ant.mxsm.netty.Heartbeat;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author mxsm
 * @Date 2018-09-13
 * desc:停跳处理
 */

public class HeartbeatHandler extends ProtobufVarint32FrameDecoder {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/*@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf)msg;

			buf.markReaderIndex();
			int length = buf.readableBytes();
			if(length < Heartbeat.HEARTBEAT.length) {
				return;
			}
			byte[] readHeartBeat = new byte[ Heartbeat.HEARTBEAT.length];
			buf.readBytes(readHeartBeat);
			if(Arrays.equals( Heartbeat.HEARTBEAT, readHeartBeat)) {
				if(logger.isInfoEnabled()) {
					logger.info("Recive Heart beat ........");
				}
				ctx.writeAndFlush(Unpooled.copiedBuffer( Heartbeat.HEARTBEAT));
				return;
			}
			buf.resetReaderIndex();
		}

		super.channelRead(ctx, msg);
	}*/

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		int length = in.readableBytes();
		if(length < Heartbeat.HEARTBEAT.length){
			return;
		}
		byte[] readHeartBeat = new byte[ Heartbeat.HEARTBEAT.length];
		in.readBytes(readHeartBeat);
		if(Arrays.equals(readHeartBeat, Heartbeat.HEARTBEAT)){
			if(logger.isInfoEnabled()) {
				logger.info("Recive Heart beat ........");
			}
			Channel channel = ctx.channel();
			if(channel != null && channel.isWritable()){
				//ctx.writeAndFlush(Unpooled.copiedBuffer( Heartbeat.HEARTBEAT));
				out.add(new Heartbeat());
			}
		}else{
			in.resetReaderIndex();
		}
		super.decode(ctx, in, out);
	}
}
