package com.apprApi.filter;

import com.apprApi.filter.wrapper.XSSRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XSSRequestFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(XSSRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        XSSRequestWrapper xssRequestWrapper = new XSSRequestWrapper(httpServletRequest);

        logger.info("XSSRequestFilter.XSSRequestWrapper :: " + xssRequestWrapper);

        chain.doFilter(xssRequestWrapper, response);

    }

    @Override
    public void destroy() {

    }

}
