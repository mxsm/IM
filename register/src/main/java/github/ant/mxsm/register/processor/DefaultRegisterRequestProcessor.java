package github.ant.mxsm.register.processor;

import com.github.mxsm.common.GeneralUtils;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import github.ant.mxsm.register.mananger.MagpieBridgeOnlineKeepingService;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/7/1
 * @Since 0.1
 */
public class DefaultRegisterRequestProcessor implements NettyRequestProcessor, AsyncNettyRequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegisterRequestProcessor.class);

    private final MagpieBridgeOnlineKeepingService mbOnlineKeepingService;

    public DefaultRegisterRequestProcessor(MagpieBridgeOnlineKeepingService mbOnlineKeepingService) {
        this.mbOnlineKeepingService = mbOnlineKeepingService;
    }

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception {

        if (ctx != null) {
            LOGGER.debug("receive request from RequestCode[{}] IP[{}]", request.getRequestCode(),
                NetUtils.parseChannelRemoteAddr(ctx.channel()));
        }

        switch (request.getRequestCode()) {
            case RequestCode.HEART_BEAT:
                return null;
            case RequestCode.MAGPIEBRIDGE_REGISTER:
                return this.registerMagpieBridge(ctx, request);
            default:
                break;
        }

        return null;
    }

    /**
     * 请求是否抛弃
     *
     * @return
     */
    @Override
    public boolean rejectRequest() {
        return false;
    }

    private RemotingCommand registerMagpieBridge(final ChannelHandlerContext ctx, final RemotingCommand request) {
        return RemotingCommandBuilder.buildResponseCommand(request.getCommandId());
    }
}
