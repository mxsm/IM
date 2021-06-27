package com.github.mxsm.common;

import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.common.exception.NotNullException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.properties.PropertiesFactory;
import org.reflections.ReflectionUtils;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since
 */
public abstract class AnnotationUtils {

    private static final Class<? extends Annotation> javaNotNull;

    static {
        javaNotNull = loadAnnotationType("javax.validation.constraints.NotNull");
    }

    public static void validatorNotNull(final Object... sources) {

        if (null == sources) {
            return;
        }

        Arrays.asList(sources).stream().forEach(source -> {
            Set<Field> fieldsAnnotatedWith = ReflectionUtils
                .getAllFields(source.getClass(), javaNotNull == null ? ReflectionUtils.withAnnotations(NotNull.class)
                    : ReflectionUtils.withAnnotations(NotNull.class, javaNotNull));
            if (CollectionUtils.isEmpty(fieldsAnnotatedWith)) {
                return;
            }
            fieldsAnnotatedWith.forEach(item -> {
                item.setAccessible(true);
                try {
                    if (item.get(source) == null) {
                        throw new NotNullException(item.getName() + " field not null");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });

    }

    private static Class<? extends Annotation> loadAnnotationType(String name) {
        try {
            return (Class<? extends Annotation>) Class.forName(name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }


}
