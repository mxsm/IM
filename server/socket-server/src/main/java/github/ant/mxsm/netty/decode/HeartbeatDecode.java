package github.ant.mxsm.netty.decode;

import java.util.Arrays;
import java.util.List;

import github.ant.mxsm.netty.Heartbeat;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class HeartbeatDecode extends ByteToMessageDecoder{
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		in.markReaderIndex();
		int length = in.readableBytes();
		
		if(length < 3) {
			return;
		}
		byte[] beats = new byte[3];
		in.readBytes(beats);
		
		if(Arrays.equals(beats, Heartbeat.HEARTBEAT)) {
			out.add(new Heartbeat());
			return;
		}
		in.resetReaderIndex();
		super.decodeLast(ctx, in, out);
	}

}
