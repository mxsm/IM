package github.ant.mxsm.netty.channel.handler;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.ant.mxsm.protocol.protobuf.Message.MessageProtobuf;
import github.mxsm.zkclient.ZookeeperClient;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * 
 * @author mxsm
 * @Date 2018-09-12 desc:
 */

public class ProtobufServerHandler extends ChannelDuplexHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ZookeeperClient zooKeeper;
	
	public ProtobufServerHandler(ZookeeperClient zooKeeper) {
		this.zooKeeper = zooKeeper;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		String path = zooKeeper.create("/user", "user".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		logger.info(path);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MessageProtobuf message = (MessageProtobuf) msg;

		System.out.println(message.getCtrlMessageId());
		ctx.writeAndFlush(message);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println(msg + "write");
		super.write(ctx, msg, promise);
	}
	
	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println( "write");
		super.channelWritabilityChanged(ctx);
	}
}
