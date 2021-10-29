package com.github.mxsm.store;

/**
 * @author mxsm
 * @Date 2021/10/29
 * @Since
 */
public enum AppendMessageStatus {
    PUT_OK,
    END_OF_FILE,
    MESSAGE_SIZE_EXCEEDED,
    PROPERTIES_SIZE_EXCEEDED,
    UNKNOWN_ERROR,
}