package com.hikvision.sc.devops.logviewer.exception;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice(basePackages = "com.hikvision.eits.logviewer")
@ResponseBody
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultExceptionHandler extends AbstractExceptionHandler {
    public DefaultExceptionHandler() {
        super();
    }
}