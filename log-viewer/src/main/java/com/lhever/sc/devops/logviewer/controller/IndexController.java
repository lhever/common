package com.lhever.sc.devops.logviewer.controller;

import com.lhever.sc.devops.logviewer.dto.FileInfo;
import com.lhever.sc.devops.logviewer.utils.CommonUtils;
import com.lhever.sc.devops.logviewer.utils.ViewUtils;
import com.lhever.sc.devops.core.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

   private   static final String regx = "^(.*?)([0-9]{4}-[0-9]{2}-[0-9]{2}.[0-9]{1,4})(.*?)$";
   private   static final String regx2 = "^(.*?)([0-9]{4}-[0-9]{2}-[0-9]{2})(.*?)$";

    @RequestMapping("/index")
    public ModelAndView realtimeLog() {
        return ViewUtils.createModelView("realtime-log", null, null);
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

        List<File> files = getAndSortFile(logBasePath);

        List<String> trs = new ArrayList<>();
        int i = 1;

        List<String> names = CollectionUtils.getNotBlank(files, f -> f.getName());

        if (CollectionUtils.isEmpty(names)) {
            return ViewUtils.createModelView("history", "content", content, "total", 0, "title", title);
        }

        String ele =
                "<tr level={level} {trClass}>" +
                "<td level={level} class=\"indexcolicon\"><img src=\"static/icon/{iconImgName}\" alt=\"[   ]\"></td>"+
                "<td level={level} class=\"indexcolname\"><a href=\"javascript:void(0)\">{lineNumber}</a></td>" +
                "<td level={level} class=\"indexcolname\"><a href=\"javascript:void(0)\">{fileName}</a></td>"+
                "<td level={level} class=\"indexcollastmod\" {serviceNameAttribute} {fileNameAttribute} {nameAttribute}>" +
                        "<a href=\"javascript:void(0)\">下载</a>" +
                "</td>" +
                "<td level={level} class=\"indexcollastmod\" {serviceNameAttribute} {fileNameAttribute} {viewAttribute}>" +
                "<a href=\"javascript:void(0)\">查看</a>" +
                "</td>" +
                "<td level={level} class=\"indexcollastmod\">&nbsp;&nbsp;&nbsp;</td>" +
                "</tr>";

        for (String fileName : names) {


            String replace = ele.replace("{lineNumber}", "" + (i++));
            replace = replace.replace("{fileName}", fileName);
            replace = replace.replace("{fileNameAttribute}", "fileName=\"" + fileName + "\"");
            replace = replace.replace("{serviceNameAttribute}", "serviceName=\"" + serviceName + "\"");
            replace = replace.replace("{nameAttribute}", "name=\"download\"");
            replace = replace.replace("{viewAttribute}", "name=\"view\"");
            replace = replace.replace("{level}", "\"" + i + "\"");

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




    public static List<File> getFileWithSuffix(String base, String suffix) {
        File[] zips = new File(base).listFiles(f -> f.getName().endsWith(suffix));
        List<File> zipList = Arrays.asList(zips);
        return zipList;
    }

    public static List<File> getFiles(String base) {
        File[] zips = new File(base).listFiles(f -> f.isFile());
        List<File> zipList = Arrays.asList(zips);
        return zipList;
    }

    public static List<File> getAndSortFile(String logBasePath) {
        List<File> files = getFiles(logBasePath);
        if (CollectionUtils.isEmpty(files)) {
            return new ArrayList<>(0);
        }

        List<File> result = new ArrayList<>(files.size());
        //按照文件后缀分组
        Map<String, List<File>> suffixMap = files.stream().collect(Collectors.groupingBy(new Function<File, String>() {
            @Override
            public String apply(File f) {
                String name = f.getName();
                int i = name.lastIndexOf(".");
                if (i > -1) {
                    return name.substring(i);
                } else {
                    return "";
                }
            }
        }, TreeMap::new, Collectors.toList()));
        Collection<List<File>> values = suffixMap.values();
        for (List<File> subList : values) {
            List<File> sort = sort(subList);
            result.addAll(sort);
        }
        if (files.size() != result.size()) {
            logger.info("文件过滤有误，基路径:{}", logBasePath);
        }
        return result;
    }


    public static List<File> sort(List<File> files) {
        List<File> result = new ArrayList<>(files.size());

        List<FileInfo> fileWithTime = new ArrayList<>(files.size());
        List<File> otherFile = new ArrayList<>(files.size());
        for (File f : files) {
            String name = f.getName();
            if (StringUtils.match(regx, name)) {
                //文件名含有日期，满足格式1
                fileWithTime.add(getFileInfoReg1(f, regx, name));
            } else if (StringUtils.match(regx2, name)) {
                //文件名含有日期，满足格式2
                fileWithTime.add(getFileInfoReg2(f, regx2, name));
            } else {
                //文件名无日期
                otherFile.add(f);
            }
        }
        //不含日期，按照文件名逆序排序
        Collections.sort(otherFile, (f1, f2) -> f2.getName().compareTo(f1.getName()));

        List<File> sortedFileWithTime = sortFileWithTime(fileWithTime);
        result.addAll(otherFile);
        result.addAll(sortedFileWithTime);
        return result;
    }

    public static List<File> sortFileWithTime(List<FileInfo> fileInfos) {
        //含有日期的，按照日期分区
        Map<Boolean, List<FileInfo>> collect = fileInfos.stream().collect(Collectors.partitioningBy(f -> f.isMatch()));

        List<File> all = new ArrayList<>(fileInfos.size());
        List<FileInfo> notMatch = collect.get(false);
        List<FileInfo> match = collect.get(true);


        if (CollectionUtils.isNotEmpty(notMatch)) {
            //分区为false的，按照文件名逆序
            Collections.sort(notMatch, (f1, f2) -> f2.getFile().getName().compareTo(f1.getFile().getName()));
            List<File> notMatchFile = notMatch.stream().map(fileInfo -> fileInfo.getFile()).collect(Collectors.toList());
            all.addAll(notMatchFile);
        }

        if (CollectionUtils.isEmpty(match)) {
            return all;
        }

        //分区为true的，按照文件名前缀（日期前面的部分文件名）分组
        Map<String, List<FileInfo>> prefixMap =
                match.stream().collect(Collectors.groupingBy(f -> f.getNamePrefix(), TreeMap::new, Collectors.toList()));
        Collection<List<FileInfo>> values = prefixMap.values();
        for (List<FileInfo> value : values) {
            //每个分组，按照日期的时间戳降序排序
            Collections.sort(value, (f1, f2) -> {
                long t1 = f1.getTimestamp();
                long t2 = f2.getTimestamp();
                long l = t1 - t2;
                if (l > 0) {
                    return -1;
                }  else if(l < 0) {
                    return 1;
                } else {
                    return 0;
                }
            });
            List<File> files = value.stream().map(fileInfo -> fileInfo.getFile()).collect(Collectors.toList());
            all.addAll(files);
        }
        return all;
    }

    public static FileInfo getFileInfoReg1(File file, String regex, String name) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(name);
            while (m.find()) {
                String prefix = m.group(1);
                String group = m.group(2);
                if (StringUtils.isNotBlank(group)) {
                    int i = group.lastIndexOf(".");
                    String date = group.substring(0, i);
                    Date d = DateFormatUtils.stringToDate(date + " 00:00:00" , "yyyy-MM-dd HH:mm:ss");
                    int seqence = StringUtils.getInt(group.substring(i+1), 0);
                    if (d !=  null ) {
                        return new FileInfo(file, true, d.getTime() + seqence, prefix);
                    }
                }
            }
        return new FileInfo(file, false, -1L, name);
    }

    public static FileInfo getFileInfoReg2(File file, String regex, String name) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(name);
        while (m.find()) {
            String group = m.group(2);
            String prefix = m.group(1);
            if (StringUtils.isNotBlank(group)) {
                Date d = DateFormatUtils.stringToDate(group + " 00:00:00" , "yyyy-MM-dd HH:mm:ss");
                if (d !=  null ) {
                    return new FileInfo(file, true, d.getTime(), prefix);
                }
            }
        }
        return new FileInfo(file, false, -1L, name);
    }






    public static void main(String[] args) {
        String name = "gateway-server-2021-06-10.1.log.zip";
        if (StringUtils.match(regx, name)) {

            System.out.println(name);

            Pattern p = Pattern.compile(regx);
            Matcher m = p.matcher(name);
            while (m.find()){
                System.out.println(m.group(0)); // 0组是整个表达式，看这里，并没有提炼出(?<!c)的字符 。结果 a3434bd
                System.out.println(m.group( 1 )); //我们只要捕获组1的数字即可。结果 3434
                System.out.println(m.group(2)); // 0组是整个表达式，看这里，并没有提炼出(?<!c)的字符 。结果 a3434bd
            }
        }

        System.out.println(JsonUtils.object2Json(getFileInfoReg1(null, regx, "gateway-server-2021-06-10.1.log.zip"), true));
        System.out.println(JsonUtils.object2Json(getFileInfoReg1(null, regx, "gateway-server-2021-06-10.11.log.zip"), true));
        System.out.println(JsonUtils.object2Json(getFileInfoReg1(null, regx, "gateway-server-2021-06-10.111.log.zip"), true));

    }




}
