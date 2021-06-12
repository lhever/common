package com.lhever.sc.devops.core.annotation;


import com.lhever.sc.devops.core.support.datasource.DynamicDataSourceAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DynamicDataSourceAspect.class)
public @interface EnableDataSourceSwitch {

}
