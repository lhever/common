package com.lhever.sc.devops.core.support.rabbitmq;

import org.springframework.util.Assert;

public class RabbitMqSender {


    private RabbitMQHelper rabbitMQHelper;

    public RabbitMqSender(RabbitMQHelper rabbitMQHelper) {
        Assert.notNull(rabbitMQHelper, "RabbitMQHelper not initialized");

        this.rabbitMQHelper = rabbitMQHelper;
    }


    public RabbitMQHelper getRabbitMQHelper() {
        return rabbitMQHelper;
    }

    public void setRabbitMQHelper(RabbitMQHelper rabbitMQHelper) {
        Assert.notNull(rabbitMQHelper, "RabbitMQHelper not initialized");
        this.rabbitMQHelper = rabbitMQHelper;
    }

    /////////////////////////////////////////////////////////////////////////////////////////


    public void sendStringFeedbackAsync(String msg) {

        rabbitMQHelper.sendStringAsync("feedback_exchange", "feedback", msg);
    }

    public void sendJsonFeedbackAsync(Object object) {
        rabbitMQHelper.toJsonAndSendAsync("feedback_exchange", "feedback", object);
    }

    public void sendStringFeedback(String msg) {
        rabbitMQHelper.sendString("feedback_exchange", "feedback", msg);

    }

    public void sendJsonFeedback(Object object) {
        rabbitMQHelper.toJsonAndSend("feedback_exchange", "feedback", object);
    }

    /////////////////////////////////////////////////////////////////////////////////////////


    public void sendStringPartnerAsync(String msg) {
        rabbitMQHelper.sendStringAsync("partner_exchange", "partner", msg);
    }

    public void sendJsonPartnerAsync(Object object) {
        rabbitMQHelper.toJsonAndSendAsync("partner_exchange", "partner", object);
    }

    public void sendStringPartner(String msg) {
        rabbitMQHelper.sendString("partner_exchange", "partner", msg);
    }

    public void sendJsonPartner(Object object) {
        rabbitMQHelper.toJsonAndSend("partner_exchange", "partner", object);
    }

    //////////////////////////////////////////////////////////////////////////////////////


    public void sendStringOperationLogAsync(String msg) {
        rabbitMQHelper.sendStringAsync("operation_log_exchange", "operation_log", msg);
    }

    public void sendJsonOperationLogAsync(Object object) {
        rabbitMQHelper.toJsonAndSendAsync("operation_log_exchange", "operation_log", object);
    }

    public void sendStringOperationLog(String msg) {
        rabbitMQHelper.sendString("operation_log_exchange", "operation_log", msg);
    }

    public void sendJsonOperationLog(Object object) {
        rabbitMQHelper.toJsonAndSend("operation_log_exchange", "operation_log", object);
    }

    //////////////////////////////////////////////////////////////////////////////////////


    public void sendStringDownloadLogAsync(String msg) {
        rabbitMQHelper.sendStringAsync("download_log_exchange", "download_log", msg);
    }

    public void sendJsonDownloadLogAsync(Object object) {
        rabbitMQHelper.toJsonAndSendAsync("download_log_exchange", "download_log", object);
    }

    public void sendStringDownloadLog(String msg) {
        rabbitMQHelper.sendString("download_log_exchange", "download_log", msg);
    }

    public void sendJsonDownloadLog(Object object) {
        rabbitMQHelper.toJsonAndSend("download_log_exchange", "download_log", object);
    }

    //////////////////////////////////////////////////////////////////////////////////////


    public void sendStringDocumentAsync(String msg) {
        rabbitMQHelper.sendStringAsync("document_exchange", "document", msg);
    }

    public void sendJsonDocumentAsync(Object object) {
        rabbitMQHelper.toJsonAndSendAsync("document_exchange", "document", object);
    }


    //////////////////////////////////////////////////////////////////////////////////////

    public void sendStringMailConfigAsync(String msg) {
        rabbitMQHelper.sendStringAsync("mail_config_exchange", "mail_config", msg);
    }

    public void sendJsonMailConfigAsync(Object object) {
        rabbitMQHelper.toJsonAndSendAsync("mail_config_exchange", "mail_config", object);
    }

    public void sendStringMailConfig(String msg) {
        rabbitMQHelper.sendString("mail_config_exchange", "mail_config", msg);
    }

    public void sendJsonMailConfig(Object object) {
        rabbitMQHelper.toJsonAndSend("mail_config_exchange", "mail_config", object);
    }


    //////////////////////////////////////////////////////////////////////////////////////

    // fanout类型的交换机不需要routing  key
    public void sendToCommonFanoutExchangeAsync(String msg) {
        rabbitMQHelper.sendStringAsync("common_fanout_exchange", "", msg);
    }

    public void sendJsonToCommonFanoutExchangeAsync(Object object) {
        rabbitMQHelper.toJsonAndSendAsync("common_fanout_exchange", "", object);
    }

    public void sendToCommonFanoutExchange(String msg) {
        rabbitMQHelper.sendString("common_fanout_exchange", "", msg);
    }

    public void sendJsonToCommonFanoutExchange(Object object) {
        rabbitMQHelper.toJsonAndSend("common_fanout_exchange", "", object);
    }

}
