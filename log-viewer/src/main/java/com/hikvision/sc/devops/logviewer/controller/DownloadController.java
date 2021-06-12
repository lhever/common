package com.hikvision.sc.devops.logviewer.controller;

import com.lhever.sc.devops.core.utils.FileUtils;
import com.hikvision.sc.devops.logviewer.utils.CommonUtils;
import com.hikvision.sc.devops.logviewer.utils.DownloadUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/22 21:53
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/22 21:53
 * @modify by reason:{方法名}:{原因}
 */
@RestController
public class DownloadController {

    @RequestMapping(path = "download", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response, String serviceName, String fileName) throws Exception {
        String logBasePath = CommonUtils.getLogBasePath(serviceName);

        String filePath = "" + FileUtils.trimTail(logBasePath) + "/" + fileName;
        if (!FileUtils.fileExists(filePath)) {
            PrintWriter writer = response.getWriter();
            writer.write("no such file");
            writer.flush();
            writer.close();
            return;
        }

        DownloadUtils.downLoad(new File(filePath), fileName, response);
    }






}
