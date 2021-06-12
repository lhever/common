package com.hikvision.sc.devops.logviewer.utils;

import com.lhever.sc.devops.core.utils.IOUtils;
import com.lhever.sc.devops.core.utils.URLUtils;
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


    public static void downLoad(File file, String fileName, HttpServletResponse response) throws Exception {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");

        //设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.setHeader("Content-Disposition", "attachment;filename=" +
                URLUtils.getURLEncoderString(fileName, "UTF-8"));

        //创建数据缓冲区
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = response.getOutputStream();
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            //将FileInputStream流写入到buffer缓冲区
            int i = bis.read(buff);
            while (i != -1) {
                //使用将OutputStream缓冲区的数据输出到客户端浏览器
                os.write(buff, 0, i);
                i = bis.read(buff);
            }
            os.flush();
        } catch (Exception e) {
            logger.error("download " + fileName + " error", e);
        } finally {
            IOUtils.closeQuietly(bis, os);
        }
    }

    public static void downLoadPdf(File file, String fileName, HttpServletResponse response) throws Exception {
//        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/pdf");

        //设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.setHeader("Content-Disposition", "attachment;filename=" +
                URLUtils.getURLEncoderString(fileName, "UTF-8"));

        //创建数据缓冲区
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = response.getOutputStream();
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            //将FileInputStream流写入到buffer缓冲区
            int i = bis.read(buff);
            while (i != -1) {
                //使用将OutputStream缓冲区的数据输出到客户端浏览器
                os.write(buff, 0, i);
                i = bis.read(buff);
            }
            os.flush();
        } catch (Exception e) {
            logger.error("download " + fileName + " error", e);
        } finally {
            IOUtils.closeQuietly(bis, os);
        }
    }


}
