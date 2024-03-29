package com.github.mxsm.remoting;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since
 */
public interface InvokeCallback<T extends Future> {

    void operationComplete(T future);

}
