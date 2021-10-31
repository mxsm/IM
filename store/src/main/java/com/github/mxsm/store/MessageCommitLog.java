package com.github.mxsm.store;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.store.config.MessagePersistentConfig;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @date 2021/10/31 17:22
 * @Since 1.0.0
 */
public class MessageCommitLog {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCommitLog.class);

    protected final MessagePersistent messagePersistent;

    protected final MappedFileQueue mappedFileQueue;

    public MessageCommitLog(MessagePersistent messagePersistent) {
        this.messagePersistent = messagePersistent;
        MessagePersistentConfig messagePersistentConfig = messagePersistent.getMessagePersistentConfig();
        this.mappedFileQueue = new MappedFileQueue(messagePersistentConfig.getStorePathCommitLog(),
            messagePersistentConfig.getMappedFileSizeCommitLog(), messagePersistent.getAllocatedFileService());
    }

    public PutMessageResult putMessage(final RemotingCommand msg) {


        return null;
    }

    public CompletableFuture<PutMessageResult> asyncPutMessage(final RemotingCommand msg) {


        return null;
    }
}
