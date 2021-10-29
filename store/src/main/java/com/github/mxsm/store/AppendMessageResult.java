package com.github.mxsm.store;

/**
 * @author mxsm
 * @Date 2021/10/29
 * @Since
 */
public class AppendMessageResult {

    private AppendMessageStatus status;

    private int wroteBytesSize;

    private long storeTimestamp;

    public AppendMessageResult(AppendMessageStatus status) {

    }

    public AppendMessageStatus getStatus() {
        return status;
    }

    public void setStatus(AppendMessageStatus status) {
        this.status = status;
    }

    public int getWroteBytesSize() {
        return wroteBytesSize;
    }

    public void setWroteBytesSize(int wroteBytesSize) {
        this.wroteBytesSize = wroteBytesSize;
    }

    public long getStoreTimestamp() {
        return storeTimestamp;
    }

    public void setStoreTimestamp(long storeTimestamp) {
        this.storeTimestamp = storeTimestamp;
    }
}
