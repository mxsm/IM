package com.github.mxsm.common.annotation;

import java.lang.annotation.*;

/**
 * 背标识的字段标识不能为空
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

}
