package com.github.mxsm.register.strategy;

import com.github.mxsm.common.magpiebridge.MagpieBridgeMetadata;
import java.util.List;

/**
 * @author mxsm
 * @date 2021/12/14 21:14
 * @Since 1.0.0
 */
public interface SelectMagpieBridgeStrategy {

    /**
     * select MagpieBridgeInfo
     * @param list
     * @return
     */
    MagpieBridgeMetadata select(List<MagpieBridgeMetadata> list);

}
