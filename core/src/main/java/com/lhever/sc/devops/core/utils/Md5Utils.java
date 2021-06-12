package com.lhever.sc.devops.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class Md5Utils {
    private static Logger logger = LoggerFactory.getLogger(Md5Utils.class);

    /**
     * MD5加密
     *
     * @param content
     * @return String
     * @throws Exception String
     * @author lihong 2016年1月1日 下午5:52:42
     * @since v1.0
     */
    public static String md5(String content) throws Exception {

        if (StringUtils.isBlank(content)) {
            return null;
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(content.getBytes("UTF-8"));
        byte[] digest = md.digest();
        StringBuffer md5 = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            md5.append(Character.forDigit((digest[i] & 0xF0) >> 4, 16));
            md5.append(Character.forDigit((digest[i] & 0xF), 16));
        }

        String endoded = md5.toString();
        return endoded;
    }

    public static String md5Quitely(String content) {
        try {
            return md5(content);
        } catch (Exception e) {
            logger.error("md5 error", e);
        }

        return null;

    }
}
