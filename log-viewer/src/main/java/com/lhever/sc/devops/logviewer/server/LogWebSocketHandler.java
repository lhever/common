package com.lhever.sc.devops.logviewer.server;

import com.lhever.sc.devops.core.utils.FileUtils;
import com.lhever.sc.devops.core.utils.IOUtils;
import com.lhever.sc.devops.logviewer.constant.LogViewerConst;
import com.lhever.sc.devops.logviewer.utils.CommonUtils;
import com.lhever.sc.devops.logviewer.utils.TimeUtils;
import com.lhever.sc.devops.logviewer.utils.WebsocketUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ysc on 01/07/2018.
 */
@Component
@ServerEndpoint(value = "/show/online/{serviceName}", configurator = HttpSessionConfigurator.class)
public class LogWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogWebSocketHandler.class);



    static class ProcessInfo {
        private Process process;
        private InputStream inputStream;
        private String serviceName;
        private long start;
        private Thread thread;
    }

    private final Map<String, ProcessInfo> processInfosBySession = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam(value = "serviceName") String serviceName,
                       Session session, EndpointConfig config) {
        ProcessInfo processInfo = new ProcessInfo();
        //session.setMaxIdleTimeout(0L);
        String wsSessionId = session.getId();
        try {
//            HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
            processInfosBySession.put(wsSessionId, processInfo);

            processInfo.serviceName = serviceName;
            processInfo.start = System.currentTimeMillis();

            logger.info("开始获取实时日志, serviceName: {}, wsSessionId: {}", serviceName, wsSessionId);

            if (StringUtils.isBlank(serviceName)) {
                WebsocketUtils.send(session, "参数serviceName未指定", true);
                return;
            }

            String logPath = CommonUtils.getLogPath(serviceName);
            if (!FileUtils.fileExists(logPath)) {
                WebsocketUtils.send(session, "日志文件不存在", true);
                return;
            }


            //所有部署服务的服务器会使用规范的日志路径，格式为：/home/ubuntu/paas/logs/项目名称/服务名称.log
            //String command = commandPrefix+" tail -f /home/ubuntu/paas/logs/rqm.log -n 6000";
            String command = getCommand(logPath);

            processInfo.process = Runtime.getRuntime().exec(command);

            processInfo.inputStream = processInfo.process.getInputStream();

            TailLogThread thread = new TailLogThread(processInfo.inputStream, session);
            processInfo.thread = thread;
            thread.setName("websocket-write-thread-" + wsSessionId);
            thread.start();
        } catch (Throwable e) {
            logger.error("获取实时日志异常", e);
            clear(processInfo);
        }
    }

    /*@OnMessage
     public void message(Session session, String data) {
        if (logger.isInfoEnabled()) {
            Supplier<String> supplier =  () -> {
                String msg =  "当前session的id是:" + session.getId() + ", 消息是: " + data;
                return msg;
            };
            logger.info(supplier.get());
        }
     }*/


    private String getCommand(String logPath) {
        String os = System.getProperty("os.name").toLowerCase();
        String command = null;

        if (os.indexOf("linux") >= 0) {
            logger.info("current os is linux");
            command = "tail  -f " + logPath + " -n " + LogViewerConst.LOG_MAX_LINE;


        } else if (os.indexOf("windows") >= 0) {
            logger.info("current os is windows");
            //windows版的tail命令，不支持-n参数
            logPath = logPath.replace("/", "\\");
            command = "tail  -f " + logPath;

        } else {
            logger.info("unknow os, exit");
            command = "echo  error";
        }
        logger.info("command: " + command);
        return command;
    }


    private void clear(ProcessInfo processInfo) {
        stopThread(processInfo.thread);
        IOUtils.closeQuietly(processInfo.inputStream);
        destroy(processInfo.process);
    }

    private void stopThread(Thread thread) {
        if (thread == null) {
            return;
        }
        if (!thread.isAlive()) {
            return;
        }

        thread.interrupt();
    }





    private void destroy(Process process) {
        if (process == null) {
            return;
        }

        try {
            process.destroy();
        } catch (Throwable e) {
            logger.error("destroy process error", e);
            try {
                process.destroyForcibly();
            } catch (Throwable ex) {
                logger.error("destroy process forcibly error", e);
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String wsSessionId = session.getId();
        logger.info("websocket close, wsSessionId: {}, reason:{} ",
                wsSessionId, (closeReason == null) ? null : closeReason.getReasonPhrase());

        if (!processInfosBySession.containsKey(wsSessionId)) {
            logger.error("can not close invalid session with id: " + wsSessionId);
            return;
        }

        ProcessInfo processInfo = processInfosBySession.remove(wsSessionId);
        if (processInfo == null) {
            return;
        }

        logger.info("停止获取实时日志, 耗时: {}, serviceName: {}, wsSessionId: {}",
                TimeUtils.getTimeDes(System.currentTimeMillis() - processInfo.start),
                processInfo.serviceName,
                wsSessionId);

       clear(processInfo);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        String wsSessionId = session.getId();
        logger.error("websocket error with wsSessionId:{} ", wsSessionId, t);

        ProcessInfo processInfo = processInfosBySession.remove(wsSessionId);
        if (processInfo == null) {
            return;
        }

        logger.info("websocket报错, 耗时: {}, serviceName: {}, wsSessionId: {}",
                TimeUtils.getTimeDes(System.currentTimeMillis() - processInfo.start),
                processInfo.serviceName,
                wsSessionId);

        clear(processInfo);
    }


    private static String getSessionProperty(HttpSession session, String propertyName) {
        try {
            return session.getAttribute(propertyName) == null ? null : session.getAttribute(propertyName).toString();
        } catch (Exception e) {
            logger.error("获取会话属性异常", e);
        }
        return null;
    }

}
