package com.lhever.sc.devops.core.support.filter;

import com.lhever.sc.devops.core.utils.MdcUtils;
import com.lhever.sc.devops.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestIdFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(RequestIdFilter.class);


    public RequestIdFilter() {
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String reqId = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            reqId = MdcUtils.getRequestId(servletRequest);
        }
        reqId = StringUtils.isBlank(reqId) ? StringUtils.getUuid() : reqId;
        MdcUtils.setRequestId(reqId);
        try {
            chain.doFilter(request, response);
        } finally {
            MdcUtils.removeRequestId();
        }
    }

    @Override
    public void destroy() {

    }


}
