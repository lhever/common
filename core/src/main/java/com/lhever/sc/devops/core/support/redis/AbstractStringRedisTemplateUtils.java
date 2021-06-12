package com.lhever.sc.devops.core.support.redis;

import com.lhever.sc.devops.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public abstract class AbstractStringRedisTemplateUtils extends AbstractRedisTemplateUtils {

    private static final Logger log = LoggerFactory.getLogger(AbstractStringRedisTemplateUtils.class);


    /**
     * 保存key,value键值对，并设置有效期。 该方法可能抛出任何异常，如果redis服务不正常的话
     *
     * @param key
     * @param value
     * @param expire 有效期
     * @param unit   有效期单位
     * @return
     * @author lihong10 2018/12/27 15:16
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 15:16
     * @modify by reason:{原因}
     */
    public static void set(String key, String value, long expire, TimeUnit unit) {
        set(key, value, expire, unit, null);
    }

    /**
     * 保存key,value键值对，并设置有效期。  该方法可能抛出任何异常，如果redis服务不正常的话
     *
     * @param key
     * @param value
     * @param expire 有效期
     * @param unit   有效期单位
     * @param msg    抛出异常时候的提示信息
     * @return
     * @author lihong10 2018/12/27 15:16
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 15:16
     * @modify by reason:{原因}
     */
    public static void set(String key, String value, long expire, TimeUnit unit, String msg) {

        try {
            template.opsForValue().set(key, value, expire, unit);
        } catch (Exception e) {
            log.error(msg + "原因:{}", "往redis插入key-value并设置有效期异常");
            if (StringUtils.isNotBlank(msg)) {
                throw new RuntimeException(msg, e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 保存key,value键值对，并设置有效期。 该方法不会抛出任何异常
     *
     * @param key
     * @param value
     * @param expire 有效期
     * @param unit   有效期单位
     * @return
     * @author lihong10 2018/12/27 15:16
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 15:16
     * @modify by reason:{原因}
     */
    public static void setQuietly(String key, String value, long expire, TimeUnit unit) {
        try {
            template.opsForValue().set(key, value, expire, unit);
        } catch (Exception e) {
            log.error("往redis插入key-value并设置有效期异常", e);
        }
    }

    /**
     * 保存key,value键值对， 该方法不会抛出任何异常
     *
     * @param key
     * @param value
     * @return
     * @author lihong10 2018/12/27 15:16
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 15:16
     * @modify by reason:{原因}
     */
    public static void setQuietly(String key, String value) {
        try {
            template.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("往redis插入key-value异常", e);
        }
    }


    /**
     * 查询与key关联的值，该方法会抛出异常
     *
     * @param key
     * @return
     * @author lihong10 2018/12/27 15:14
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 15:14
     * @modify by reason:{原因}
     */
    public static String get(String key) {
        return get(key, null);
    }


    /**
     * 查询与key关联的值，该方法会抛出异常
     *
     * @param key
     * @param msg 抛出异常时候的提示信息
     * @return
     * @author lihong10 2018/12/27 15:14
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 15:14
     * @modify by reason:{原因}
     */
    public static String get(String key, String msg) {

        try {
            String value = (String) template.opsForValue().get(key);
            return value;
        } catch (Exception e) {
            if (StringUtils.isNotBlank(msg)) {
                throw new RuntimeException(msg, e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getQuietly(String key) {

        try {
            String value = get(key, null);
            return value;
        } catch (Exception e) {
            log.error("get error: ", e);
        }
        return null;
    }

    public static boolean delete(String key) {
        return  delete(key, null);
    }


    /**
     * 删除与key关联的键值对，该方法可能会抛出异常
     *
     * @param key
     * @return
     * @author lihong10 2018/12/27 15:12
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 15:12
     * @modify by reason:{原因}
     */
    public static boolean delete(String key, String msg) {
        try {
            return template.delete(key);
        } catch (Exception e) {
            if (StringUtils.isNotBlank(msg)) {
                throw new RuntimeException(msg, e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 删除与key关联的键值对，不会抛出任何异常
     *
     * @param key
     * @return
     * @author lihong10 2018/12/27 15:12
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/27 15:12
     * @modify by reason:{原因}
     */
    public static boolean deleteQuitely(String key) {
        Boolean success = false;
        try {
            success = template.delete(key);
        } catch (Exception e) {
            log.error("delete error: ", e);
        }
        return success;
    }
}
