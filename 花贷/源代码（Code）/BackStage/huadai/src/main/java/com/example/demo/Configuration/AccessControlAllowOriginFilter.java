package com.example.demo.Configuration;


import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;


/*
*
*       由于本文使用的服务器是本地电脑实现内网穿透而实现的   ,
*       故进行配置，放置重定向 url 拦截
*
* */

@Configuration
public class AccessControlAllowOriginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(req, response);
    }

    public void init(FilterConfig filterConfig) {

    }

    public void destroy() {

    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}

