package github.ant.mxsm.netty.channel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.ant.mxsm.protocol.protobuf.Message.MessageProtobuf;
import github.mxsm.zkclient.ZookeeperClient;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author mxsm
 * @Date 2018-09-12 desc:
 */

public class ProtobufServerHandler extends ChannelDuplexHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProtobufServerHandler.class);

	private ZookeeperClient zkClient;
	
	public ProtobufServerHandler(ZookeeperClient zkClient) {
		this.zkClient = zkClient;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("channel {} active ",ctx.channel().toString());
		}
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel());
		System.out.println(ctx.channel().close());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(LOGGER.isErrorEnabled()) {
			LOGGER.error(cause.getMessage());
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MessageProtobuf message = (MessageProtobuf) msg;

		System.out.println(message.getCtrlMessageId());
		ctx.writeAndFlush(message);
	}
}
