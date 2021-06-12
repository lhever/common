package com.lhever.sc.devops.core.config;

import com.lhever.sc.devops.core.utils.WebConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author hehaoneng 2019/4/11 10:06
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2019/4/11 10:06
 * @modify by reason:{方法名}:{原因}
 */
@Configuration
@EnableWebMvc
public class CommonWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(WebConfigUtils.getMappingJackson2HttpMessageConverter());
    }


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<MediaType> supportedMediaTypes = converters.get(0).getSupportedMediaTypes();

        List<MediaType> li = new ArrayList<>(supportedMediaTypes);
        li.add(MediaType.TEXT_PLAIN);
        ((AbstractGenericHttpMessageConverter) converters.get(0)).setSupportedMediaTypes(li);

        ///////begin////////
        //这里是另一种方式处理响应HTTP头的MediaType为字符串格式的数据
        // 目前之所以采用上面的方式是因为多增加一个HttpMessageConverter，会多一次converter
        // 比如目前是响应头中accept是String类型(text/plain)，那么会先拿到json的converter，发现响应头不对（application/json），再去拿String的converter
        //上面的方式是在json的converter中添加了支持String类型的响应数据，这样的话，需要在上面configureMessageConverters()方法中对mappingJackson2HttpMessageConverter进行定制处理
//        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
//        stringConverter.setWriteAcceptCharset(false);
//        converters.add(stringConverter);
        ///////end//////////
    }
}
