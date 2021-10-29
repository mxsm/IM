package com.github.mxsm.store.utils;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/10/29
 * @Since
 */
public abstract class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * make sure dir exist
     * @param dirName
     */
    public static void ensureDirOK(String dirName){
        if(dirName != null){
            File file = new File(dirName);
            if(!file.exists()){
                boolean mkdirs = file.mkdirs();
                LOGGER.info("{} mkdir {}",dirName,mkdirs?"OK":"FALSE");
            }
        }
    }

}
