package github.ant.mxsm.netty.channel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author mxsm
 * @Date 2018-09-12 desc:
 */

public class ProtobufServerHandler extends ChannelDuplexHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(msg.getClass());
	}
	
}
