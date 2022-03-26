package com.github.mxsm.common.utils;

import java.util.zip.CRC32;

/**
 * General utils
 * @author mxsm
 * @Date 2021/7/1
 * @Since 1.0.0
 */
public abstract class GeneralUtils {

    public static int crc32(byte[] array) {
        if (array != null) {
            return crc32(array, 0, array.length);
        }

        return 0;
    }

    public static int crc32(byte[] array, int offset, int length) {
        CRC32 crc32 = new CRC32();
        crc32.update(array, offset, length);
        return (int) (crc32.getValue() & 0x7FFFFFFF);
    }

    public static int getAvailableProcessors(){
        return Runtime.getRuntime().availableProcessors();
    }

}
