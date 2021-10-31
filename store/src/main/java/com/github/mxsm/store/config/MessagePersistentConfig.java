package com.github.mxsm.store.config;

import com.github.mxsm.common.annotation.NotNull;
import java.io.File;

/**
 * @author mxsm
 * @date 2021/10/31 17:28
 * @Since 1.0.0
 */
public class MessagePersistentConfig {

    @NotNull
    private String storePathRootDir = System.getProperty("user.home") + File.separator + "persistent";

    //The directory in which the commitlog is kept
    @NotNull
    private String storePathCommitLog = System.getProperty("user.home") + File.separator + "persistent"
        + File.separator + "commitlog";

    // CommitLog file size,default is 1G -- refer to  rocket mq
    private int mappedFileSizeCommitLog = 1024 * 1024 * 1024;

    public String getStorePathRootDir() {
        return storePathRootDir;
    }

    public void setStorePathRootDir(String storePathRootDir) {
        this.storePathRootDir = storePathRootDir;
    }

    public String getStorePathCommitLog() {
        return storePathCommitLog;
    }

    public void setStorePathCommitLog(String storePathCommitLog) {
        this.storePathCommitLog = storePathCommitLog;
    }

    public int getMappedFileSizeCommitLog() {
        return mappedFileSizeCommitLog;
    }

    public void setMappedFileSizeCommitLog(int mappedFileSizeCommitLog) {
        this.mappedFileSizeCommitLog = mappedFileSizeCommitLog;
    }
}
