package com.lhever.sc.devops.logviewer.controller;

import com.lhever.sc.devops.logviewer.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/23 19:29
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/23 19:29
 * @modify by reason:{方法名}:{原因}
 */
@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response) {
        writeLog();
        return "success";
    }


    public void writeLog() {

        Runnable r = () -> {

            LOGGER.info("开始写日志");
            long start = System.currentTimeMillis();
            int len = 100000;
            for (int i = 0; i < len; i++) {
                try {
                    if (i % 33333 == 0) {
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    LOGGER.error("线程等待异常", e);
                }
                LOGGER.info("开始处理，步骤: {}", i);
                if (i % 2 == 1) {
                    LOGGER.info("处理错误: 步骤：{}", i);
                } else {
                    LOGGER.error("处理错误: 步骤：{}", i);
                }
            }
            String timeStr = TimeUtils.getTimeDes(System.currentTimeMillis() - start);
            LOGGER.info("写日志结束，花费的时间是: " + timeStr);
        };

        Thread t = new Thread(r);
        t.start();
    }
}
