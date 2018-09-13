package github.ant.mxsm.netty;

import java.io.Serializable;

public class Heartbeat implements Serializable{
	
	public static final byte[] HEARTBEAT = {0x10,0x11,0x12};
	
	private static final long serialVersionUID = -4980570969930361880L;

}
