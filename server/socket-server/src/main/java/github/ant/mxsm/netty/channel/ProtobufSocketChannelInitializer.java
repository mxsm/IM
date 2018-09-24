package github.ant.mxsm.netty.channel;

import github.ant.mxsm.netty.channel.handler.HeartbeatHandler;
import github.ant.mxsm.netty.channel.handler.ProtobufServerHandler;
import github.ant.mxsm.protocol.protobuf.Message;
import github.mxsm.zkclient.ZookeeperClient;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 
 * @author mxsm
 * @Date 2018-09-14
 * desc:ChannelInitializer
 */

public class ProtobufSocketChannelInitializer extends SocketChannelInitializer{

	private final ZookeeperClient zkClient;
	
	public ProtobufSocketChannelInitializer(ZookeeperClient zkClient) {
		this.zkClient = zkClient;
	}
	
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast("heartbeatHandler", new HeartbeatHandler())//心跳处理
		        .addLast("protobufVarint32FrameDecoder",new ProtobufVarint32FrameDecoder())//protobuf头部解码
		        .addLast("protobufFrameDecoder", new ProtobufDecoder(Message.MessageProtobuf.getDefaultInstance()))//protobuf解码
		        .addLast("frameEncode", new ProtobufVarint32LengthFieldPrepender())//protobuf头部长度编码
		        .addLast("encode", new ProtobufEncoder())//protobuf消息体编码
		        .addLast("protobufHandler", new ProtobufServerHandler(this.zkClient)); //逻辑处理

	}

}
