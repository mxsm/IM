package github.ant.mxsm.register.mananger;

import com.github.mxsm.remoting.ChannelEventListener;
import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class MagpieBridgeOnlineKeepingService implements ChannelEventListener {

    private MagpieBridgeManager magpieBridgeManager;

    public MagpieBridgeOnlineKeepingService(MagpieBridgeManager magpieBridgeManager) {
        this.magpieBridgeManager = magpieBridgeManager;
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRegistered()} to forward to the next {@link ChannelInboundHandler}
     * in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelRegistered(String remoteAddress, Channel channel) throws Exception {

    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward to the next {@link
     * ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param channel
     */
    @Override
    public void onChannelUnregistered(Channel channel) throws Exception {

    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward to the next {@link ChannelInboundHandler} in
     * the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param channel
     */
    @Override
    public void onChannelActive(Channel channel) throws Exception {

    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward to the next {@link ChannelInboundHandler} in
     * the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param channel
     */
    @Override
    public void onChannelInactive(Channel channel) throws Exception {

    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward to the next {@link ChannelHandler}
     * in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param channel
     * @param cause
     */
    @Override
    public void onExceptionCaught(Channel channel, Throwable cause) throws Exception {

    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward to the next {@link
     * ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param channel
     * @param evt
     */
    @Override
    public void onUserEventTriggered(Channel channel, Object evt) throws Exception {

    }

    public MagpieBridgeManager getMagpieBridgeManager() {
        return magpieBridgeManager;
    }
}
