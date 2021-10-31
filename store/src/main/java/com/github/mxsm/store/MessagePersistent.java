package com.github.mxsm.store;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.store.config.MessagePersistentConfig;
import java.util.concurrent.CompletableFuture;

/**
 * @author mxsm
 * @date 2021/10/31 16:55
 * @Since 1.0.0
 */
public interface MessagePersistent {

    boolean load();

    PutMessageResult putMessage(final RemotingCommand msg);

    default CompletableFuture<PutMessageResult> asyncPutMessage(final RemotingCommand command) {
        return CompletableFuture.completedFuture(putMessage(command));
    }

    MessagePersistentConfig getMessagePersistentConfig();


    AllocateMappedFileService getAllocatedFileService();
}
