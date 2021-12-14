package com.github.mxsm.common;

import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.common.exception.NotNullException;
import com.github.mxsm.common.utils.AnnotationUtils;
import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since
 */
class AnnotationUtilsTest {

    @NotNull
    private String test;

    @Test
    void validator() {
        Set<Field> allFields = ReflectionUtils.getAllFields(A.class, ReflectionUtils.withAnnotations(NotNull.class,
            javax.validation.constraints.NotNull.class));
        Assertions.assertThrows(NotNullException.class, ()-> AnnotationUtils.validatorNotNull(new A()));
    }

}