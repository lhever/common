package com.lhever.sc.devops.core.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 利用枚举定义上下文工具类
 */
public enum ContextUtils {
    //INSTANCE是ContextUtils的唯一实例
    INSTANCE;

    private ContextUtils() {
    }

    private ApplicationContext context;

    static {
        ContextUtils[] values = ContextUtils.values();
        if (values.length != 1) {
            throw new IllegalArgumentException("ContextUtils 是单列工具类， 不允许有多个枚举实例");
        }
    }

    public static void setApplicationContext(ApplicationContext context) {
        if (context == null) {
            throw new IllegalArgumentException("ApplicationContext null");
        }
        INSTANCE.context = context;
    }

    public static ApplicationContext getContext() {
        return INSTANCE.context;
    }

    public final static Object getBean(String beanName) {
        return INSTANCE.context.getBean(beanName);
    }

    public final static Object getBean(String beanName, Class<?> requiredType) {
        return INSTANCE.context.getBean(beanName, requiredType);
    }

    public final static <T> T getBean(Class<T> requiredType, String beanName) {
        return INSTANCE.context.getBean(beanName, requiredType);
    }

    public final static <T> T getBean(Class<T> requiredType) {
        return INSTANCE.context.getBean(requiredType);
    }

    public final static boolean containsBean(String beanName) {
        return INSTANCE.context.containsBean(beanName);
    }

    public static String[] getBeanNamesForType(@Nullable Class<?> type) {
        return INSTANCE.context.getBeanNamesForType(type);
    }

    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) {
        return INSTANCE.context.getBeansOfType(type);
    }


    public static List<Map<String, String>> getAllRequestMapping(String name) {

        if (!ContextUtils.containsBean(name)) {
            return new ArrayList<>(0);
        }
        Object bean = INSTANCE.context.getBean(name);
        if (bean == null || !(bean instanceof RequestMappingHandlerMapping)) {
            return new ArrayList<>(0);
        }

        RequestMappingHandlerMapping requestHandler = (RequestMappingHandlerMapping) bean;
        return getAllRequestMapping(requestHandler);
    }


    public static List<Map<String, String>> getAllRequestMapping(RequestMappingHandlerMapping requestHandler) {
        if (requestHandler == null) {
            return new ArrayList<>(0);
        }
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestHandler.getHandlerMethods();
        if (handlerMethods == null) {
            return new ArrayList<>(0);
        }

        List<Map<String, String>> list = new ArrayList();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {

            Map<String, String> apiInfo = new HashMap();
            HandlerMethod method = entry.getValue();
            apiInfo.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            apiInfo.put("classMethod", method.getMethod().getName()); // 方法名

            ////////////////////////////////////////////////////////////

            RequestMappingInfo requestMapping = entry.getKey();
            RequestMethodsRequestCondition methodsCondition = requestMapping.getMethodsCondition();
            String methodType = methodsCondition.getMethods().stream().map(m -> m.toString()).collect(Collectors.joining(", "));
            apiInfo.put("httpMethod", methodType);

            PatternsRequestCondition patternsCondition = requestMapping.getPatternsCondition();
            String urlString = new ArrayList<String>(patternsCondition.getPatterns()).stream().collect(Collectors.joining(", "));

            apiInfo.put("url", urlString);

            list.add(apiInfo);
        }
        return list;
    }

    public static Map<String, List<Map<String, String>>> getAllRequestMappingMap() {

        String[] beanNamesForType = INSTANCE.context.getBeanNamesForType(RequestMappingHandlerMapping.class);
        Map<String, List<Map<String, String>>> apis = new HashMap<>();
        Arrays.stream(beanNamesForType).forEach(
                beanName -> {
                    List<Map<String, String>> allRequestMapping = getAllRequestMapping(beanName);
                    apis.put(beanName, allRequestMapping);
                }
        );
        return apis;
    }


    public static List<Map<String, String>> getAllRequestMappingList() {

        String[] beanNamesForType = INSTANCE.context.getBeanNamesForType(RequestMappingHandlerMapping.class);
        List<Map<String, String>> apis = new ArrayList<>();
        Arrays.stream(beanNamesForType).forEach(
                beanName -> {
                    List<Map<String, String>> allRequestMapping = getAllRequestMapping(beanName);
                    apis.addAll(allRequestMapping);
                }
        );
        return apis;
    }


}
