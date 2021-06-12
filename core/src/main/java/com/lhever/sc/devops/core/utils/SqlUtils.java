package com.lhever.sc.devops.core.utils;


import com.lhever.sc.devops.core.constant.CommonConstants;
import org.springframework.util.StringUtils;

/**
 * @author lihong10
 * @creatTime 2018/8/10 20:39
 */
public class SqlUtils {

    /**
     * 转义特殊字符, 该方法仅用于Postgres数据库，若用于其他关系型数据库，请新增方法 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     * @author lihong10 2019/1/4 11:34
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2019/1/4 11:34
     * @modify by reason:{原因}
     */
    public static String escapeForPostgres(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return keyword;
        }
        if (keyword.contains("\\")) {
            keyword = keyword.replaceAll("\\\\", "\\\\\\\\");
        }

        String[] fbsArr = {"\"", "/", "%", "_", "\'"};
        for (String key : fbsArr) {
            if (keyword.contains(key)) {
                keyword = keyword.replaceAll(key, "\\\\" + key);
            }
        }


        return keyword;
    }


    public static String like(String keyword) {
        return CommonConstants.PERCENT + keyword + CommonConstants.PERCENT;
    }

    public static String escapedLike(String keyword) {
        return CommonConstants.PERCENT + escapeForPostgres(keyword) + CommonConstants.PERCENT;
    }

    public static String tailLike(String keyword) {
        return keyword + CommonConstants.PERCENT;
    }

    public static String escapedTailLike(String keyword) {
        return escapeForPostgres(keyword) + CommonConstants.PERCENT;
    }

    public static String headLike(String keyword) {
        return CommonConstants.PERCENT + keyword;
    }

    public static String escapedHeadLike(String keyword) {
        return CommonConstants.PERCENT + escapeForPostgres(keyword);
    }





    /**
     * 拼接字符串
     *
     * @param words
     * @return
     * @author lihong10 2019/1/4 12:07
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2019/1/4 12:07
     * @modify by reason:{原因}
     */
    public static String concat(String... words) {
        if (words == null) {
            return CommonConstants.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            if (word != null) { //此处不适合采用StringUtils.isNotBlank判空
                builder.append(word);
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(escapeForPostgres("\\"));
        System.out.println(escapeForPostgres("\\"));
        System.out.println(escapeForPostgres("/"));
        System.out.println(escapeForPostgres("%"));
        System.out.println(escapeForPostgres("_"));
    }
}
