package com.lhever.sc.devops.core.support.advice;

import com.lhever.sc.devops.core.annotation.HeaderFieldMapping;
import com.lhever.sc.devops.core.annotation.HeaderFieldMappings;
import com.lhever.sc.devops.core.utils.OgnlUtils;
import com.lhever.sc.devops.core.utils.RequestContextUtils;
import com.lhever.sc.devops.core.utils.StringUtils;
import com.lhever.sc.devops.core.utils.OgnlUtils;
import com.lhever.sc.devops.core.utils.RequestContextUtils;
import com.lhever.sc.devops.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;


@Configuration
@ControllerAdvice(basePackages = "com.hikvision.community.health")
public class CustomRequestBodyAdvice implements RequestBodyAdvice {
    private final static Logger log = LoggerFactory.getLogger(CustomRequestBodyAdvice.class);


    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        if (body == null) {
            return body;
        }

        HttpServletRequest request = RequestContextUtils.getRequest();
        if (request == null) {
            return body;
        }


        Class<?> bodyClass = body.getClass();
        /**
         * 这两个注解只识别其中一个,如果直接在当前类型上发现的话
         */
        HeaderFieldMapping mapping = null;
        HeaderFieldMappings mappings = null;

        if ((mapping = AnnotationUtils.getAnnotation(bodyClass, HeaderFieldMapping.class)) != null) {
            setValue(request, body, mapping);

        } else if ((mappings = AnnotationUtils.getAnnotation(bodyClass, HeaderFieldMappings.class)) != null) {
            setValue(request, body, mappings);

        } else {
            Class<?> superclass = bodyClass.getSuperclass();
            while (superclass != Object.class) {
                mapping = AnnotationUtils.getAnnotation(superclass, HeaderFieldMapping.class);
                if (mapping != null) {
                    setValue(request, body, mapping);
                }


                mappings = AnnotationUtils.getAnnotation(superclass, HeaderFieldMappings.class);
                if (mappings != null) {
                    setValue(request, body, mappings);
                }
                superclass = superclass.getSuperclass();
            }
        }

        return body;
    }


    public void setValue(HttpServletRequest request, Object body, HeaderFieldMappings mappings) {
        HeaderFieldMapping[] lst = mappings.value();
        if (lst != null && lst.length > 0) {
            for (HeaderFieldMapping ele : lst) {
                setValue(request, body, ele);
            }
        }
    }


    private void setValue(HttpServletRequest request, Object body, HeaderFieldMapping mapping) {


        String fieldName = mapping.field();
        String header = mapping.header();
        Class<?> type = mapping.fieldType();

        String headerValue = request.getHeader(header);

        if (StringUtils.isBlank(headerValue)) {
            log.error("request no header: {}", header);
            return;
        }

        if (type.equals(String.class)) {
            OgnlUtils.setValue(fieldName, body, headerValue);

        } else if (type.equals(Long.class)) {
            long longVal = Long.parseLong(headerValue);
            OgnlUtils.setValue(fieldName, body, longVal);

        } else if (type.equals(Integer.class)) {
            int intVal = Integer.parseInt(headerValue);
            OgnlUtils.setValue(fieldName, body, intVal);

        } else if (type.equals(Short.class)) {
            short shortVal = Short.parseShort(headerValue);
            OgnlUtils.setValue(fieldName, body, shortVal);

        } else if (type.equals(Boolean.class)) {
            boolean bool = Boolean.parseBoolean(headerValue);
            OgnlUtils.setValue(fieldName, body, bool);

        } else if (type.equals(Double.class)) {
            double doubleV = Double.parseDouble(headerValue);
            OgnlUtils.setValue(fieldName, body, doubleV);

        } else if (type.equals(Float.class)) {
            float f = Float.parseFloat(headerValue);
            OgnlUtils.setValue(fieldName, body, f);

        } else if (type.equals(Byte.class)) {
            byte b = Byte.parseByte(headerValue);
            OgnlUtils.setValue(fieldName, body, b);
        }


    }


    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
