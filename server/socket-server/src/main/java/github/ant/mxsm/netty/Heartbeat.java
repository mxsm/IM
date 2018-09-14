package github.ant.mxsm.netty;

import java.io.Serializable;

/**
 * 
 * @author mxsm
 * @Date 2018-09-14
 * desc:心跳
 */

public class Heartbeat implements Serializable{
	
	private static final long serialVersionUID = -4980570969930361880L;

	public static final byte[] HEARTBEAT = {0x10,0x11,0x12};
}
