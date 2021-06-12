package com.lhever.sc.devops.core.support.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2019/5/8 9:30
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2019/5/8 9:30
 * @modify by reason:{方法名}:{原因}
 */
public abstract class BaseService {
    private final Class<?> clazz = this.getClass();
    public final Logger logger = LoggerFactory.getLogger(clazz);

}
