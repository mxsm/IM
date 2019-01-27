package github.ant.mxsm.netty.socket.server;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.ant.mxsm.netty.channel.ProtobufSocketChannelInitializer;
import github.ant.mxsm.protocol.protobuf.Message.MessageProtobuf;
import github.mxsm.zkclient.ZookeeperClient;
import github.mxsm.zkclient.serializer.ProtobufSerializer;
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
	
	private static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
	
	private static final long DEFAULT_CONNECTIONTIMEOUT = 60;
	
	private int port;
	
	private ZookeeperClient zkClient;
	
	private EventLoopGroup bossGroup;
	
	private EventLoopGroup workGroup;
	
	private ServerBootstrap bootstrap;
	
	private String zookeeperServers;
	
	private long connectionTimeout;
	
	private TimeUnit connectionTimeoutTimeUnit;
	
	public SocketStarter(ZookeeperClient zkClient) {
		this(DEFAULT_PORT, zkClient,DEFAULT_CONNECTIONTIMEOUT,DEFAULT_TIMEUNIT);
	}
	
	public SocketStarter(String zookeeperServers) {
		this(DEFAULT_PORT, zookeeperServers,DEFAULT_CONNECTIONTIMEOUT,DEFAULT_TIMEUNIT);
	}
	
	public SocketStarter(final int port,final String zookeeperServers,long connectionTimeout, TimeUnit connectionTimeoutTimeUnit) {
		this.port = port;
		this.zookeeperServers = zookeeperServers;
		this.connectionTimeout = connectionTimeout;
		this.connectionTimeoutTimeUnit = connectionTimeoutTimeUnit;
		init();
	}

	public SocketStarter(final int port,final ZookeeperClient zkClient,long connectionTimeout, TimeUnit connectionTimeoutTimeUnit) {
		this.port = port;
		this.zkClient = zkClient;
		this.connectionTimeout = connectionTimeout;
		this.connectionTimeoutTimeUnit = connectionTimeoutTimeUnit;
		init();
	}
	
	public void init() {

		bossGroup = new NioEventLoopGroup();
		workGroup = new NioEventLoopGroup();
		if(port == 0) {
			port = DEFAULT_PORT;
		}
		if(StringUtils.isBlank(zookeeperServers) && zkClient == null) {
			throw new IllegalArgumentException("zookeeperServers is null");
		}
		if(connectionTimeout <= 0) {
			connectionTimeout = DEFAULT_CONNECTIONTIMEOUT;
		}
		if(connectionTimeoutTimeUnit == null) {
			connectionTimeoutTimeUnit = DEFAULT_TIMEUNIT;
		}
		
		bootstrap = new ServerBootstrap();
		if(zkClient == null) {
			zkClient = new ZookeeperClient(zookeeperServers, connectionTimeout, connectionTimeoutTimeUnit);
		}
		zkClient.setZkSerializer(new ProtobufSerializer<>(MessageProtobuf.getDefaultInstance()));
		
		if(logger.isInfoEnabled()) {
			logger.info("Netty start Info [port={}]",port);
		}
		
		if(logger.isInfoEnabled()) {
			logger.info("zookeeper client start Info [zookeeperServers={} connectionTimeout={} connectionTimeoutTimeUnit ={}]",zookeeperServers,connectionTimeout,connectionTimeoutTimeUnit);
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
		ChannelFuture future = this.bootstrap.bind(port).syncUninterruptibly();
		if(logger.isInfoEnabled()) {
			logger.info("IM Server start succss, bind port={}",this.port);
		}
		future.channel().closeFuture().sync();
	}
	
	
}
