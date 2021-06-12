package com.hikvision.sc.devops.logviewer.utils;

import com.lhever.sc.devops.core.utils.FileUtils;
import com.lhever.sc.devops.core.utils.OgnlUtils;
import com.lhever.sc.devops.core.utils.YamlUtils;
import com.hikvision.sc.devops.logviewer.constant.LogViewerConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);


    public static String getLogBasePath(String serviceName) {
        serviceName = serviceName.trim();
        String basePath = serviceName + "." + LogViewerConst.LOG_BASE_PATH_NAME;

        Map<String, Object> yamlMap = getYamlMap();
        Map<String, Object> fileMap = OgnlUtils.getValue("log.files", yamlMap);

        String logBasePath = OgnlUtils.getValue(basePath, fileMap);

        return logBasePath;
    }


    public static String getLogPath(String serviceName) {
        serviceName = serviceName.trim();
        String basePath = serviceName + "." + LogViewerConst.LOG_BASE_PATH_NAME;
        String rollingFileName = serviceName + "." + LogViewerConst.ROLLING_FILE_NAME;

        Map<String, Object> yamlMap = getYamlMap();
        Map<String, Object> fileMap = OgnlUtils.getValue("log.files", yamlMap);

        String logFilePath = OgnlUtils.getValue(basePath, fileMap);
        String logFileName = OgnlUtils.getValue(rollingFileName, fileMap);

        String fullName = "" + FileUtils.trimTail(logFilePath) + "/" + logFileName;

        logger.info("log path: " + fullName);

        return fullName;
    }


    public static Map<String, Object> getYamlMap() {
        Map<String, Object> yamlMap = YamlUtils.yaml2Map(LogViewerConst.CONFIG_FILE_NAME, false);
        return yamlMap;
    }



}
