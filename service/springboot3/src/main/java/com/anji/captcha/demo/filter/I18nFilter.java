package com.anji.captcha.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "i18nFilter", urlPatterns = "/*")
public class I18nFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String language = request.getHeader("accept-language");
        if (language == null) {
            language = "zh-CN";//en-US
        }
        Locale locale = Locale.forLanguageTag(language);
        // 设置语言环境
        LocaleContextHolder.setLocale(locale);
        // 继续执行过滤器链
        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}