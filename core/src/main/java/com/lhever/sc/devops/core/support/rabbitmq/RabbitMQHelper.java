package com.lhever.sc.devops.core.support.rabbitmq;

import com.lhever.sc.devops.core.support.threadpool.SimpleThreadPoolService;
import com.lhever.sc.devops.core.utils.JsonUtils;
import com.lhever.sc.devops.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMQHelper {

    private static Logger log = LoggerFactory.getLogger(RabbitMQHelper.class);

    private SimpleThreadPoolService simpleThreadPoolService;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQHelper() {
    }

    public RabbitMQHelper(SimpleThreadPoolService simpleThreadPoolService, RabbitTemplate rabbitTemplate) {
        this.simpleThreadPoolService = simpleThreadPoolService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public SimpleThreadPoolService getSimpleThreadPoolService() {
        return simpleThreadPoolService;
    }

    public void setSimpleThreadPoolService(SimpleThreadPoolService simpleThreadPoolService) {
        this.simpleThreadPoolService = simpleThreadPoolService;
    }

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendString(String exchangeName, String routingKey, String msg) {

        if (StringUtils.isBlank(msg)) {
            return;
        }

        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
    }

    public void toJsonAndSend(String exchangeName, String routingKey, Object msg) {

        if (msg == null) {
            return;
        }
        String json = JsonUtils.object2Json(msg);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, json);

    }

    public void sendStringAsync(String exchangeName, String routingKey, String msg) {

        SendTask task = new SendTask(rabbitTemplate, exchangeName, routingKey, msg);
        simpleThreadPoolService.submitAndExecute(task);
    }

    public void toJsonAndSendAsync(String exchangeName, String routingKey, Object msg) {
        if (msg == null) {
            return;
        }
        String json = JsonUtils.object2Json(msg);

        SendTask task = new SendTask(rabbitTemplate, exchangeName, routingKey, json);
        simpleThreadPoolService.submitAndExecute(task);
    }


    public static class SendTask implements Runnable {
        private RabbitTemplate rabbitTemplate;
        private String exchangeName;
        private String routingKey;
        private String msg;

        public SendTask(RabbitTemplate rabbitTemplate, String exchangeName, String routingKey, String msg) {
            this.rabbitTemplate = rabbitTemplate;
            this.exchangeName = exchangeName;
            this.routingKey = routingKey;
            this.msg = msg;
        }

        @Override
        public void run() {
            if (msg == null) {
                return;
            }
            try {
                rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
