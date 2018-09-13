package github.ant.mxsm.netty.channel;

import github.ant.mxsm.netty.channel.handler.HeartbeatHandler;
import github.ant.mxsm.netty.channel.handler.ProtobufServerHandler;
import github.ant.mxsm.protocol.protobuf.Message;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class ProtobufSocketChannelInitializer extends SocketChannelInitializer{

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast("heartbeatHandler", new HeartbeatHandler())
		        .addLast("protobufVarint32FrameDecoder",new ProtobufVarint32FrameDecoder())
		        .addLast("protobufFrameDecoder", new ProtobufDecoder(Message.MessageProtobuf.getDefaultInstance()))
		        .addLast("frameEncode", new ProtobufVarint32LengthFieldPrepender())
		        .addLast("encode", new ProtobufEncoder())
		        .addLast("protobufHandler", new ProtobufServerHandler());
	}

}
