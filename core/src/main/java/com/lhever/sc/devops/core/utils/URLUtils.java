package com.lhever.sc.devops.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLUtils {

    private static final Logger log = LoggerFactory.getLogger(URLUtils.class);

    private final static String CHAR_SET = "UTF-8";
    private final static String EMPTY = "";


    public static String getURLDecodeString(String str) throws Exception {
        if (str == null) {
            return EMPTY;
        }
        String result = java.net.URLDecoder.decode(str, CHAR_SET);

        return result;
    }

    public static String getURLDecodeString(String str, String charset) throws Exception {
        if (str == null) {
            return EMPTY;
        }

        charset = StringUtils.isBlank(charset) ? CHAR_SET : charset;
        String result = java.net.URLDecoder.decode(str, charset);

        return result;
    }


    public static String getURLEncoderString(String str) throws Exception {
        if (null == str) {
            return EMPTY;
        }
        String result = java.net.URLEncoder.encode(str, CHAR_SET);

        return result;
    }

    public static String getURLEncoderString(String str, String charset) throws Exception {
        if (null == str) {
            return EMPTY;
        }
        charset = StringUtils.isBlank(charset) ? CHAR_SET : charset;
        String result = java.net.URLEncoder.encode(str, charset);

        return result;
    }


}
