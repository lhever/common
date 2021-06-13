package com.lhever.sc.devops.logviewer.test;

import com.lhever.sc.devops.logviewer.constant.LogViewerConst;
import com.lhever.sc.devops.core.utils.*;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/22 14:21
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/22 14:21
 * @modify by reason:{方法名}:{原因}
 */
public class YamlTest {



    private static final String CONFIG_FILE_NAME = "/application.yml";

    public static  Map<String, Object> QRCODE_URL_MAP;



    static {

    }



    @Test
    public void test() {

        Map<String, Object> yamlMap = YamlUtils.yaml2Map(LogViewerConst.CONFIG_FILE_NAME, false);
        Map<String, Object> log = OgnlUtils.getValue("log.files", yamlMap);

        System.out.println(JsonUtils.object2Json(log));

        String value = OgnlUtils.getValue("rqmWeb.basePath", log);
        System.out.println(value);

        value = OgnlUtils.getValue("rqmWeb.basePath.ssss", log);
        System.out.println(value);


        value = OgnlUtils.getValue("ssss.basePath.ssss", log);
        System.out.println(value);


    }

    @Test
    public void testT() throws UnsupportedEncodingException {
        List<String> stringList = FileUtils.readLinesByClassPathResource("/tcp.txt");
        StringBuilder builder = new StringBuilder();
        stringList.stream()
                .filter(line -> StringUtils.isNotBlank(line))
                .map(line -> line.trim()).forEach(
                line -> {
                    String[] arr = line.split("\\s+");

                    for (int i = 0; i < arr.length; i++) {
                        if (i == 0) {
                            continue;
                        }

                        String ele = arr[i];
                        if (StringUtils.isBlank(ele)) {
                            continue;
                        }

                        ele = ele.trim();

                        if (ele.length() == 4) {

                            int i1 = Integer.parseInt(ele.substring(0, 2), 16);
                            builder.append((char) i1);

                            int i2 = Integer.parseInt(ele.substring(2), 16);
                            builder.append((char) i2);
                        }

                    }
                }

        );

        System.out.println(builder.toString());

        System.out.println("-----------------------------------------");

        String deurl = URLDecoder.decode(builder.toString(),"UTF-8");
        System.out.println(deurl);


    }


    @Test
    public void test2() {
        System.out.println(Integer.parseInt("1f", 16));

        String encrypt = JasyptUtils.encrypt("cc1892253ce84f3ea0efecbc5dfa4be4", "Abc12345");
        String encrypt1 = JasyptUtils.encrypt("cc1892253ce84f3ea0efecbc5dfa4be4", "logger");
        System.out.println(encrypt);
        System.out.println(encrypt1);

            for (int i = 0; i < (99 / 2); i ++) {
                System.out.println(2 * i + "   " + (2 * i + 1));
            }
    }











}
