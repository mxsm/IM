package com.github.mxsm.store;

/**
 * @author mxsm
 * @date 2021/10/31 16:57
 * @Since 1.0.0
 */
public enum PutMessageStatus {
    PUT_OK,
    FLUSH_DISK_TIMEOUT,
    FLUSH_SLAVE_TIMEOUT,
    SLAVE_NOT_AVAILABLE,
    SERVICE_NOT_AVAILABLE,
    CREATE_MAPEDFILE_FAILED,
    MESSAGE_ILLEGAL,
    PROPERTIES_SIZE_EXCEEDED,
    OS_PAGECACHE_BUSY,
    UNKNOWN_ERROR,
}
