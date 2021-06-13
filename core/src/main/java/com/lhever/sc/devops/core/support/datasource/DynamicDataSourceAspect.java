package com.lhever.sc.devops.core.support.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

@Aspect
@Configuration
@Order(1)
public class DynamicDataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * 对指定注解，进行横切，创建一个横切的对象方法
     */
    @Pointcut("@annotation(com.lhever.sc.devops.core.support.datasource.DS)")
    public void dsAnnotationPoint() {
    }

    @Before("dsAnnotationPoint()")
    public void beforeSwitchDS(JoinPoint point) {
        log.info("AOP切换数据源");


        //获得当前访问的class
        //Class<?> cls = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 得到访问的方法对象
        Method method = signature.getMethod();

        DataSourceType dataSource = DataSourceContextHolder.getDB();

        // 判断是否存在@DS注解
        if (method.isAnnotationPresent(DS.class)) {
            DS annotation = method.getAnnotation(DS.class);
            // 取出注解中的数据源名
            dataSource = DataSourceType.getByType(annotation.value());
        }
        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);
    }


    @After("dsAnnotationPoint()")
    public void afterSwitchDS(JoinPoint point) {
        log.info("AOP清理数据源");
        DataSourceContextHolder.clearDB();
    }
}
