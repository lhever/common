package com.lhever.sc.devops.logviewer.filter;

import com.lhever.sc.devops.logviewer.constant.LogViewerConst;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();

        //静态资源路径，放行
        if (requestURI.startsWith("/log/static/") || requestURI.startsWith("/static/")) {
            chain.doFilter(req, rep);
            return;
        }

        //登录登出或根路径，放行
        if (requestURI.equals("/") || requestURI.equals("/log/loginPage") || requestURI.equals("/log/login") || requestURI.equals("/log/logout")) {
            chain.doFilter(req, rep);
            return;
        }

        //websocket路径，放行
        if (requestURI.startsWith("/log/show/online/")) {
            chain.doFilter(req, rep);
            return;
        }

       judge(req, rep, chain);
    }

    public void judge(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)req).getSession(false);
        if (session == null || session.getAttribute(LogViewerConst.CURRENT_USER) == null) {
            ((HttpServletResponse) rep).sendRedirect("/log/loginPage"); //重定向到登录页
        } else {
            chain.doFilter(req, rep);
        }
    }


}
