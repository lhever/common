package com.lhever.sc.devops.logviewer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 提供给EUREKA注册中心查看服务信息的controller
 * </p>
 *
 * @author hehaoneng 2018/12/4 20:16
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2018/12/4 20:16
 * @modify by reason:{方法名}:{原因}
 */
@Controller
public class LogErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(LogErrorController.class);
    public static final int NOT_FOUND = 404;
    public static final int NOT_SUPPORT = 405;


    /**
     * 出异常后进入该方法，交由下面的方法处理
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * ErrorFilter不会拦截404的请求， 404的请求通过该接口进行响应
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/error")
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        logger.info("javax.servlet.error.status_code: {}", status);

        if (status == NOT_FOUND) {
            return new ModelAndView("redirect:loginPage");
        }

        String msg = null;
        if (status == NOT_SUPPORT) {
            msg = "不支持的方法类型";
        } else {
            msg = "内部服务器错误,正在处理";
        }

        Map<String, Object> data = new HashMap<>(4);
        data.put("mesg", msg);
        data.put("code", "" + status);
        data.put("data", null);

        return data;
    }



}
