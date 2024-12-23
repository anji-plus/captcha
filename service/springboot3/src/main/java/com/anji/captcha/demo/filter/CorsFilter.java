/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.demo.filter;
/**
 * 本地启动解决跨域问题
 *  打包发布请注释该类，可能会造成一定的影响，解决本地启动跨域问题
 * Created by raodeming on 2020/3/17.
 */
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter ")
@Configuration
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public  void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT, OPTIONS, DELETE");//http请求方式
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}