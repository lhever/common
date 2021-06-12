package com.lhever.sc.devops.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author jianghaitao6$ 2019/3/2$ 14:50$
 * @return
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2019/3/2$ 14:50$
 * @modify by reason:{原因}
 */

public class DownloadUtils {

    public static final Logger logger = LoggerFactory.getLogger(DownloadUtils.class);
    public static final int BUFFER_SIZE = 8192;

    public static void downLoad(File file, String fileName, HttpServletResponse response) {

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");

        //设置content-disposition响应头控制浏览器以下载的形式打开文件
        FileInputStream is = null;
        OutputStream os = null;
        try {
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLUtils.getURLEncoderString(fileName, "UTF-8"));
            is = new FileInputStream(file);
            os = response.getOutputStream();
            copy(new BufferedInputStream(is), os);
        } catch (Exception e) {
            logger.error("download " + fileName + " error:", e);
        } finally {
            IOUtils.closeQuietly(is, os);
        }
    }


    public static void downLoad(InputStream is, String fileName, HttpServletResponse response) {

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");

        //设置content-disposition响应头控制浏览器以下载的形式打开文件
        OutputStream os = null;
        try {
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLUtils.getURLEncoderString(fileName, "UTF-8"));
            os = response.getOutputStream();
            if (is instanceof BufferedInputStream) {
                copy(is, os);
            } else {
                copy(new BufferedInputStream(is), os);
            }
        } catch (Exception e) {
            logger.error("download " + fileName + " error:", e);
        } finally {
            IOUtils.closeQuietly(is, os);
        }
    }


    public static int copy(InputStream in, OutputStream out) throws IOException {
        int byteCount = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            byteCount += bytesRead;
        }
        out.flush();
        return byteCount;
    }











}
