package com.github.mxsm.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 背标识的字段标识不能为空
 * @author mxsm
 * @Date 2021/6/20
 * @Since 1.0.0
 */
@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

}
