package com.hikvision.sc.devops.logviewer.controller;

import com.lhever.sc.devops.core.utils.CollectionUtils;
import com.lhever.sc.devops.core.utils.FileUtils;
import com.lhever.sc.devops.core.utils.StringUtils;
import com.hikvision.sc.devops.logviewer.utils.CommonUtils;
import com.hikvision.sc.devops.logviewer.utils.ViewUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/21 20:38
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/21 20:38
 * @modify by reason:{方法名}:{原因}
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public ModelAndView realtimeLog() {
        return ViewUtils.createModelView("realtime-log", null, null);
    }


    @RequestMapping("/logs")
    public ModelAndView logList(@RequestParam(value = "serviceName", required = false) String serviceName) {
        String content = "";
        String title = "日志不存在";
        if (StringUtils.isBlank(serviceName)) {
            return ViewUtils.createModelView("logs", "content", content, "total", 0, "title", title);
        }

        String logBasePath = CommonUtils.getLogBasePath(serviceName);
        if (StringUtils.isBlank(logBasePath) || !FileUtils.dirExists(logBasePath)) {
            return ViewUtils.createModelView("logs", "content", content, "total", 0, "title", title);
        }

        File dir = new File(logBasePath);
        File[] files = dir.listFiles();


        List<String> trs = new ArrayList<>();
        int i = 1;

        List<String> names = Arrays
                .stream(files)
                .filter(f -> FileUtils.fileExists(f))
                .map(f -> f.getName())
                .sorted()
                .collect(Collectors.toList());
        Collections.reverse(names);
        names = reCombine(names);

        if (CollectionUtils.isEmpty(names)) {
            return ViewUtils.createModelView("logs", "content", content, "total", 0, "title", title);
        }

        String ele =
                "<tr {trClass}>" +
                        "<td>{lineNumber}</td>" +
                        "<td>{fileName}</td>" +
                        "<td {serviceNameAttribute} {fileNameAttribute} {nameAttribute}><a>下载</a></td>" +
                        "</tr>";
        for (String fileName : names) {
            String replace = ele.replace("{lineNumber}", "" + (i++));
            replace = replace.replace("{fileName}", fileName);
            replace = replace.replace("{fileNameAttribute}", "fileName=\"" + fileName + "\"");
            replace = replace.replace("{serviceNameAttribute}", "serviceName=\"" + serviceName + "\"");
            replace = replace.replace("{nameAttribute}", "name=\"download\"");

            if (i % 2 == 1) {
                replace = replace.replace("{trClass}", "class=\"odd\"");
            } else {
                replace = replace.replace("{trClass}", "");
            }

            trs.add(replace);
        }

        content = StringUtils.join(trs, "\n\r");
        return ViewUtils.createModelView("logs", "content", content, "total", (i - 1), "title", "日志列表");

    }





    @RequestMapping("/history")
    public ModelAndView history(@RequestParam(value = "serviceName", required = false) String serviceName) {
        String content = "";
        String title = "日志不存在";
        if (StringUtils.isBlank(serviceName)) {
            return ViewUtils.createModelView("history", "content", content, "total", 0, "title", title);
        }

        String logBasePath = CommonUtils.getLogBasePath(serviceName);
        if (StringUtils.isBlank(logBasePath) || !FileUtils.dirExists(logBasePath)) {
            return ViewUtils.createModelView("history", "content", content, "total", 0, "title", title);
        }

        File dir = new File(logBasePath);
        File[] files = dir.listFiles();

        List<String> trs = new ArrayList<>();
        int i = 1;

        List<String> names = Arrays
                .stream(files)
                .filter(f -> FileUtils.fileExists(f))
                .map(f -> f.getName())
                .sorted()
                .collect(Collectors.toList());
        Collections.reverse(names);
        names = reCombine(names);

        if (CollectionUtils.isEmpty(names)) {
            return ViewUtils.createModelView("history", "content", content, "total", 0, "title", title);
        }

        String ele =
                "<tr {trClass}>" +
                "<td class=\"indexcolicon\"><img src=\"static/icon/{iconImgName}\" alt=\"[   ]\"></td>"+
                "<td class=\"indexcolname\"><a href=\"javascript:void(0)\">{lineNumber}</a></td>" +
                "<td class=\"indexcolname\"><a href=\"javascript:void(0)\">{fileName}</a></td>"+
                "<td class=\"indexcollastmod\" {serviceNameAttribute} {fileNameAttribute} {nameAttribute}>" +
                        "<a href=\"javascript:void(0)\">下载</a>" +
                "</td>" +
                "<td class=\"indexcollastmod\">&nbsp;&nbsp;&nbsp;</td>" +
                "</tr>";

        for (String fileName : names) {


            String replace = ele.replace("{lineNumber}", "" + (i++));
            replace = replace.replace("{fileName}", fileName);
            replace = replace.replace("{fileNameAttribute}", "fileName=\"" + fileName + "\"");
            replace = replace.replace("{serviceNameAttribute}", "serviceName=\"" + serviceName + "\"");
            replace = replace.replace("{nameAttribute}", "name=\"download\"");

            if (fileName.endsWith(".zip")) {
                replace = replace.replace("{iconImgName}", "compressed.gif");
            } else {
                replace = replace.replace("{iconImgName}", "text.gif");
            }

            if (i % 2 == 1) {
                replace = replace.replace("{trClass}", "class=\"odd\"");
            } else {
                replace = replace.replace("{trClass}", "class=\"even\"");
            }

            trs.add(replace);
        }
        content = StringUtils.join(trs, "\n\r");

        return ViewUtils.createModelView("history", "content", content, "total", (i -1), "title", "日志列表");
    }

    public List<String> reCombine(List<String> names) {
        if (CollectionUtils.isEmpty(names)) {
            return names;
        }

        List<String> zipList = names.stream().filter(i -> i.endsWith(".zip")).collect(Collectors.toList());
        List<String> notZipLst = names.stream().filter(i -> !i.endsWith(".zip")).collect(Collectors.toList());

        notZipLst.addAll(zipList);
        return notZipLst;
    }




}
