package com.hikvision.sc.devops.logviewer.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by ysc on 01/07/2018.
 */
@Component
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {


    private static volatile BeanFactory context;

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
          HttpSession httpSession = (HttpSession) request.getHttpSession();
          sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException, BeansException {
        return context.getBean(clazz);
    }

}
