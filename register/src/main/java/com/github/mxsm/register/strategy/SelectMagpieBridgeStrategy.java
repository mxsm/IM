package com.github.mxsm.register.strategy;


import com.github.mxsm.protocol.protobuf.ServerMetadata;
import java.util.Collection;

/**
 * @author mxsm
 * @date 2021/12/14 21:14
 * @Since 1.0.0
 */
public interface SelectMagpieBridgeStrategy {

    /**
     * select MagpieBridgeInfo
     * @param mbCollections
     * @return
     */
    ServerMetadata select(Collection<? extends ServerMetadata> mbCollections);

}
