package com.lhever.sc.devops.core.utils;

import com.lhever.sc.devops.core.constant.CommonConstants;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author guoliang5
 * @desc 应用级对象获取工具类
 * @since 9/18/2018 3:00 PM
 */
public class RequestContextUtils {

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    public static String getRequestId() {
        HttpServletRequest request = getRequest();
        return getRequestId(request);
    }

    public static String getRequestId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String reqId = request.getHeader(CommonConstants.REQUEST_ID);
        if (StringUtils.isNotBlank(reqId)) {
            return reqId;
        }

        return MdcUtils.getRequestId();
    }

    public static String getIp() {
        return NetUtils.getIp(getRequest());
    }


    public static String getIp(HttpServletRequest request) {
        return NetUtils.getIp(request);
    }


}