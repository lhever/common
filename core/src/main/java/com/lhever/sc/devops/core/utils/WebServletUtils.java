package com.lhever.sc.devops.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class WebServletUtils {
    private final static Logger log = LoggerFactory.getLogger(WebServletUtils.class);
    private final static String CHARSET_UTF8 = "UTF-8";
    private final static int BUFFER_SIZE = 512;

    private WebServletUtils() {
    }

    public static void setEncoding(HttpServletRequest request, String charset) {
        if (request == null) {
            return;
        }
        charset = StringUtils.isBlank(charset) ? CHARSET_UTF8 : charset;

        try {
            request.setCharacterEncoding(charset);
        } catch (UnsupportedEncodingException e) {
            log.error("set request encoding error ", e);
        }

    }


    public static String readAndClose(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String body = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = toBytesAndClose(inputStream);
            body = new String(bytes, CHARSET_UTF8);
        } catch (IOException e) {
            log.error("read body error", e);
        }
        return body;
    }

    public static String readAndClose(HttpServletRequest request, String charset) {
        if (request == null) {
            return null;
        }
        String body = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = toBytesAndClose(inputStream);
            body = new String(bytes, charset);
        } catch (IOException e) {
            log.error("read body error", e);
        }
        return body;
    }

    public static String readNoClose(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String body = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = toBytesNoClose(inputStream);
            body = new String(bytes, CHARSET_UTF8);
        } catch (IOException e) {
            log.error("read body error", e);
        }
        return body;
    }

    public static byte[] toBytesAndClose(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        }

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[512];
        int bytesRead = -1;
        while ((bytesRead = in.read(data)) != -1) {
            outStream.write(data, 0, bytesRead);
        }
        outStream.flush();
        data = null;

        byte[] bytes = outStream.toByteArray();

        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(outStream);
        return bytes;
    }

    public static byte[] toBytesNoClose(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        }

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[512];
        int bytesRead = -1;
        while ((bytesRead = in.read(data)) != -1) {
            outStream.write(data, 0, bytesRead);
        }
        outStream.flush();
        data = null;

        byte[] bytes = outStream.toByteArray();
        IOUtils.closeQuietly(outStream);
        return bytes;
    }

    /**
     * 将请求重定向到redirectUrl指定的页面
     *
     * @param res
     * @param redirectUrl
     * @return
     * @author lihong10 2018/12/17 18:22
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2018/12/17 18:22
     * @modify by reason:{原因}
     */
    public static void redirect(HttpServletResponse res, String redirectUrl) {
        if (res == null || StringUtils.isBlank(redirectUrl)) {
            return;
        }
        try {
            res.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new IllegalStateException("redirect error", e);
        }
    }


}
