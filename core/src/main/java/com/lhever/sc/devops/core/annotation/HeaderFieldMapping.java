package com.lhever.sc.devops.core.annotation;

import java.lang.annotation.*;

/**
 * {@code HeaderFieldMapping}: 该注解用于指定http请求头（请求头通过header字段定义）中的值应该赋值给对象的哪一个属性（属性名字通过field字段定义）
 *
 * @author lihong10 2018/9/29 14:23:00
 * return
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface HeaderFieldMapping {

    /**
     * 请求头的名字
     *
     * @return
     */
    String header();

    /**
     * 类的字段名字
     *
     * @return
     */
    String field();

    /**
     * 字段的类型
     *
     * @return
     */
    Class<?> fieldType() default String.class;

    /**
     * 请求头中的值是否需要解密
     *
     * @return
     */
    boolean needDecrypt() default false;

    /**
     * 如果需要解密， 该字段指定了解密算法
     *
     * @return
     */
    String decryptAlgorithm() default "";
}
