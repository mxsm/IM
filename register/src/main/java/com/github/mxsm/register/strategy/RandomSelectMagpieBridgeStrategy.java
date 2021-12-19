package com.github.mxsm.register.strategy;

import com.github.mxsm.common.magpiebridge.MagpieBridgeMetadata;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.collections4.CollectionUtils;

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
    public MagpieBridgeMetadata select(Collection<? extends MagpieBridgeMetadata> mbCollections) {

        if(CollectionUtils.isEmpty(mbCollections)){
            return null;
        }
        ArrayList<? extends MagpieBridgeMetadata> magpieBridgeMetadatas = new ArrayList<>(mbCollections);
        Collections.shuffle(magpieBridgeMetadatas);
        return magpieBridgeMetadatas.get(0);
    }
}
