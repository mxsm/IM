package com.github.mxsm.register.strategy;

import com.github.mxsm.protocol.protobuf.ServerMetadata;
import java.util.Collection;

/**
 * @author mxsm
 * @date 2021/12/19 14:07
 * @Since 1.0.0
 */
public class RandomSelectMagpieBridgeStrategy implements SelectMagpieBridgeStrategy{

    /**
     * select MagpieBridgeInfo
     *
     * @param mbCollections
     * @return
     */
    @Override
    public ServerMetadata select(Collection<? extends ServerMetadata> mbCollections) {
        return null;
    }

}
