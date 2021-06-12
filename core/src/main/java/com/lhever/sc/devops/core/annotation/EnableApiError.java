package com.lhever.sc.devops.core.annotation;


import com.lhever.sc.devops.core.support.controller.CustomErrorController;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CustomErrorController.class)
public @interface EnableApiError {

    boolean print() default false;
}
