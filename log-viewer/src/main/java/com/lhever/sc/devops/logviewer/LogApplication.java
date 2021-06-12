package com.lhever.sc.devops.logviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by ysc on 01/07/2018.
 */
//@EnableWebMvc
@SpringBootApplication(exclude = {
        //禁止数据源自动配置
        RabbitAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class,
}, scanBasePackages = {"com.hikvision.sc"})
public class LogApplication /*extends SpringBootServletInitializer*/ {
    public static final long START_TIME = System.currentTimeMillis();

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RealtimeLogApplication.class);
	}*/
}
