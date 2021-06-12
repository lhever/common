package com.hikvision.sc.devops.logviewer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/23 20:34
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/23 20:34
 * @modify by reason:{方法名}:{原因}
 */
public class WebsocketUtils {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketUtils.class);


    public static void send(Session session, String content, boolean linereturn) {
        if (content == null) {
            return;
        }
        if (linereturn) {
            content = content + "<br>";
        }

        try {
            //如果不同步，多线程写，汇报到如下异常
            //java.lang.IllegalStateException:
            // The remote endpoint was in state [TEXT_FULL_WRITING] which is an invalid state for called method
                session.getBasicRemote().sendText(content);
        } catch (Exception e) {
            logger.error("send content error", e);
        }
    }


}
