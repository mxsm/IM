package com.github.mxsm.store.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @date 2021/10/31 16:40
 * @Since 1.0.0
 */
class FileUtilTest {

    @Test
    void ensureDirOK() {

    }

    @Test
    void offset2FileName() {

        String s = FileUtil.offset2FileName(10);
        Assertions.assertEquals("00000000000000000010", s);

    }
}