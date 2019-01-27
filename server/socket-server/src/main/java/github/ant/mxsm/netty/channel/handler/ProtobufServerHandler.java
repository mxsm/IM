package github.ant.mxsm.netty.channel.handler;

import github.ant.mxsm.netty.Heartbeat;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
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

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ZookeeperClient zkClient;

	public ProtobufServerHandler(ZookeeperClient zooKeeper) {
		this.zkClient = zooKeeper;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		//处理心跳
		if(msg instanceof Heartbeat){
			ctx.writeAndFlush(Heartbeat.HEARTBEAT);
			return;
		}
		//处理Message
		MessageProtobuf message = (MessageProtobuf) msg;

		zkClient.createEphemeral("/user", message);
		
		MessageProtobuf messagea = zkClient.readData("/user");

		ctx.writeAndFlush(messagea);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage());
	}

}
