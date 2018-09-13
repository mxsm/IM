package github.ant.mxsm.netty.channel.handler;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.ant.mxsm.netty.Heartbeat;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author mxsm
 * @Date 2018-09-13
 * desc:
 */

public class HeartbeatHandler extends ChannelDuplexHandler{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		logger.info(msg.getClass().getName());
		
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
					logger.info("Heart beat ........");
				}
				ByteBuf encode = ctx.alloc().buffer();
				ctx.writeAndFlush(encode.writeBytes( Heartbeat.HEARTBEAT));
				return;
			}
		}
		super.channelRead(ctx, msg);
	}
	
}
