package com.lhever.sc.devops.core.annotation;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface HeaderFieldMappings {
    HeaderFieldMapping[] value() default {};
}
