package com.lhever.sc.devops.core.support.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    /**
     * 默认数据源
     */
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>() {


        @Override
        protected DataSourceType initialValue() {
            return DataSourceType.DEFAULT;
        }
    };

    // 设置数据源名
    public static void setDB(DataSourceType dbType) {
        log.debug("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static DataSourceType getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}