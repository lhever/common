package com.hikvision.sc.devops.logviewer.listener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/23 22:21
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/23 22:21
 * @modify by reason:{方法名}:{原因}
 */
@WebListener
@Component
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ((HttpServletRequest) sre.getServletRequest()).getSession();

    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }
}
