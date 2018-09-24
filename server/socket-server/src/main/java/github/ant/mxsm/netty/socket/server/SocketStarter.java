package github.ant.mxsm.netty.socket.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.ant.mxsm.netty.channel.ProtobufSocketChannelInitializer;
import github.mxsm.zkclient.ZookeeperClient;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author mxsm
 * @Date 2018-09-12
 * desc:
 */

public class SocketStarter {

	private static final Logger logger = LoggerFactory.getLogger(SocketStarter.class);
	
	private static final int DEFAULT_PORT = 9980;
	
	private static final int DEFAULT_BOSSGROUPTHREAD = 16;
	
	private static final int DEFAULT_WORKGROUPTHREAD = 16;
	
	private int port;
	
	private int bossGroupThread;
	
	private int workGroupThread;
	
	private final ZookeeperClient zkClient = new ZookeeperClient("127.0.0.1:2181",5,TimeUnit.SECONDS);
	
	private EventLoopGroup bossGroup;
	
	private EventLoopGroup workGroup;
	
	private ServerBootstrap bootstrap;
	
	public SocketStarter() {
		this(DEFAULT_PORT, DEFAULT_BOSSGROUPTHREAD, DEFAULT_WORKGROUPTHREAD);
	}
	
	public SocketStarter(final int port, final int bossGroupThread, final int workGroupThread) {
		this.port = port;
		this.bossGroupThread = bossGroupThread;
		this.workGroupThread = workGroupThread;
		init();
	}

	public void init() {
		if(bossGroupThread == 0) {
			bossGroupThread = DEFAULT_BOSSGROUPTHREAD;
		}
		bossGroup = new NioEventLoopGroup(bossGroupThread);
		if(workGroupThread == 0) {
			workGroupThread = DEFAULT_WORKGROUPTHREAD;
		}
		workGroup = new NioEventLoopGroup(workGroupThread);
		if(port == 0) {
			port = DEFAULT_PORT;
		}
		bootstrap = new ServerBootstrap();
		
		if(logger.isInfoEnabled()) {
			logger.info("Netty start Info [port={} bossGroupThread={} workGroupThread ={}]",port,bossGroupThread,workGroupThread);
		}
	}

	/*
	 * 启动Netty
	 */
	public void start() throws Exception{
		
		this.bootstrap.group(this.bossGroup, this.workGroup);
		this.bootstrap.channel(NioServerSocketChannel.class);
		this.bootstrap.childHandler(new ProtobufSocketChannelInitializer(zkClient));
		this.bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		this.bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		this.bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		ChannelFuture future = this.bootstrap.bind(port).sync();
		if(logger.isInfoEnabled()) {
			logger.info("IM Server start succss, bind port={}",this.port);
		}
		future.channel().closeFuture().sync();
	}
	
	
}
