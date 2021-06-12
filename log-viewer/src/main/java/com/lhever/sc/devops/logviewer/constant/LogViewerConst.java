package com.lhever.sc.devops.logviewer.constant;

import com.lhever.sc.devops.core.config.YamlPropertiesReader;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/22 10:47
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/22 10:47
 * @modify by reason:{方法名}:{原因}
 */
public class LogViewerConst {

    public static final String CONFIG_FILE_NAME = "/application.yml";

    private static final YamlPropertiesReader reader = new YamlPropertiesReader(CONFIG_FILE_NAME, false);

    public static final int PORT = reader.getIntProperty("server.port", 444);

    public static final int LOG_MAX_LINE = reader.getIntProperty("log.websocket.maxLine", 10000);

    public static final String HOST = reader.getStringProperty("server.host", "127.0.0.1");

    public static final String CONTEXT_PATH = reader.getStringProperty("server.servlet.context-path", "/log");

    public static final String LOG_BASE_PATH_NAME = "basePath";

    public static final String ROLLING_FILE_NAME = "rollingFileName";

    public static final String FILE_DESCRIPTION = "description";


    public static final String WEB_SOCKET_ADDRESS = reader.getStringProperty("log.websocket.address", "ws://" + HOST + ":" + PORT + CONTEXT_PATH);

    public static final String CURRENT_USER = "CURRENT_USER";











}
