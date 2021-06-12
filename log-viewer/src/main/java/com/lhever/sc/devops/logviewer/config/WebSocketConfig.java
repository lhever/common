package com.lhever.sc.devops.logviewer.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/21 22:58
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/21 22:58
 * @modify by reason:{方法名}:{原因}
 */

@Configuration
@ConditionalOnWebApplication
public class WebSocketConfig  {

    //使用boot内置tomcat时需要注入此bean



}
