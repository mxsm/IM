package com.github.mxsm.common;

import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.common.exception.NotNullException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since
 */
class AnnotationUtilsTest {

    @Test
    void validator() {
        Assertions.assertThrows(NotNullException.class, ()-> AnnotationUtils.validatorNotNull(new A()));
    }

    /**
     * @author mxsm
     * @Date 2021/6/20
     * @Since
     */
    public static class A {
        @NotNull
        private String a;

        @NotNull
        public String b = "1";
    }
}