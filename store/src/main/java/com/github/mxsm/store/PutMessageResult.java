package com.github.mxsm.store;

/**
 * @author mxsm
 * @date 2021/10/31 16:57
 * @Since 1.0.0
 */
public class PutMessageResult {

    private PutMessageStatus putMessageStatus;

    private AppendMessageResult appendMessageResult;

    public PutMessageResult(PutMessageStatus putMessageStatus, AppendMessageResult appendMessageResult) {
        this.putMessageStatus = putMessageStatus;
        this.appendMessageResult = appendMessageResult;
    }

    public boolean isOk() {
        return this.appendMessageResult != null && this.appendMessageResult.isOk();
    }

    public AppendMessageResult getAppendMessageResult() {
        return appendMessageResult;
    }

    public void setAppendMessageResult(AppendMessageResult appendMessageResult) {
        this.appendMessageResult = appendMessageResult;
    }

    public PutMessageStatus getPutMessageStatus() {
        return putMessageStatus;
    }

    public void setPutMessageStatus(PutMessageStatus putMessageStatus) {
        this.putMessageStatus = putMessageStatus;
    }

    @Override
    public String toString() {
        return "PutMessageResult [putMessageStatus=" + putMessageStatus + ", appendMessageResult="
            + appendMessageResult + "]";
    }

}
