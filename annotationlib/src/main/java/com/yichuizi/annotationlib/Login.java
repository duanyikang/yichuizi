package com.yichuizi.annotationlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者： duanyikang on 2019/1/7.
 * 描述：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Login {
}
