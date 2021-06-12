package com.lhever.sc.devops.core.support.feign;

import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {


    /**
     * <li>配置feign客户端的日志级别，检验该配置是否生效的方式是断点feign.Logger的方法：protected void logRequest(String configKey, Level logLevel, request request)
     * 是否被执行。</li>
     *
     * <li>另外，想要真正看到日志，还需要logback的日志级别被设置为debug</li>
     *
     * @author lihong10 2018/11/22 19:34:00
     * return
     */
    @Bean
    @ConditionalOnProperty(name = "debug", havingValue = "true")
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }
}
