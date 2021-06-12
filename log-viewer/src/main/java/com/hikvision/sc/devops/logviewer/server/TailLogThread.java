package com.hikvision.sc.devops.logviewer.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by ysc on 01/07/2018.
 */
public class TailLogThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(TailLogThread.class);

    private BufferedReader reader;
    private Session session;

    public TailLogThread(InputStream inputStream, Session session) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.session = session;
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                try {
                    session.getBasicRemote().sendText(line + "<br>");
                } catch (IOException e) {
                    LOGGER.error("发送实时日志异常", e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("读取实时日志异常", e);
        }
        LOGGER.info("发送实时日志线程退出");
    }
}
