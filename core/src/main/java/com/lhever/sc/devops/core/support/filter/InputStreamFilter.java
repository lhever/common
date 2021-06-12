package com.lhever.sc.devops.core.support.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class InputStreamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest && ((HttpServletRequest) request).getMethod().equalsIgnoreCase("POST")) { //判断是否是http请求
            requestWrapper = new InputStreamWrapper((HttpServletRequest) request); //再封装request
        }
        if (requestWrapper != null) {
            chain.doFilter(requestWrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
