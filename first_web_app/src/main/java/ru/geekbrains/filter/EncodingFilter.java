package ru.geekbrains.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns="/*")
public class EncodingFilter implements Filter{

    private transient FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //добавляем общие параметры
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        chain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}