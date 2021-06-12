package com.lhever.sc.devops.logviewer;

import com.lhever.sc.devops.core.utils.ContextUtils;
import com.lhever.sc.devops.core.utils.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ysc on 01/07/2018.
 */
//@EnableWebMvc
@SpringBootApplication(exclude = {
        //禁止数据源自动配置
        RabbitAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class,
}, scanBasePackages = {"com.lhever.sc"})
public class LogApplication /*extends SpringBootServletInitializer*/ {
    public static final long START_TIME = System.currentTimeMillis();

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LogApplication.class, args);
        ContextUtils.setApplicationContext(run);
        List<Map<String, String>> allRequestMappingList = ContextUtils.getAllRequestMappingList();
        System.out.println(JsonUtils.object2Json(allRequestMappingList, true));
        Set<String> url = allRequestMappingList.stream().map(i -> i.get("url").trim().split("/")[1]).collect(Collectors.toSet());
        System.out.println(url);
    }

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RealtimeLogApplication.class);
	}*/
}
