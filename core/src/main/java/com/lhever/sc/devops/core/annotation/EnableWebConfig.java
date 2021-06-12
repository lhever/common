package com.lhever.sc.devops.core.annotation;


import com.lhever.sc.devops.core.config.CommonWebConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CommonWebConfig.class)
public @interface EnableWebConfig {
}
