package com.hikvision.sc.devops.logviewer.config;

import com.hikvision.sc.devops.logviewer.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean loginFilterRegistration() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter(new LoginFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        registration.setOrder(10);

        return registration;
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        /** 设置视图路径的前缀 */
        resolver.setPrefix("/WEB-INF/jsp/");
        /** 设置视图路径的后缀 */
        resolver.setSuffix(".jsp");
        return resolver;
    }

}
