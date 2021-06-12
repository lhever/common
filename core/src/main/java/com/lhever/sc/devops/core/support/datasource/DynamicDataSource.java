package com.lhever.sc.devops.core.support.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {

        log.info("获取类型为{}的数据源", DataSourceContextHolder.getDB());

        return DataSourceContextHolder.getDB();
    }

}
