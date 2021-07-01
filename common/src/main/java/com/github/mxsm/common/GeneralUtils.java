package com.github.mxsm.common;

import java.util.zip.CRC32;

/**
 * 通用的工具类
 * @author mxsm
 * @Date 2021/7/1
 * @Since 0.1
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

}
