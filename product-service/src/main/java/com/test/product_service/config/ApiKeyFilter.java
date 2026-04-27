package com.test.product_service.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    @Value("${security.api-key}")
    private String apiKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (((HttpServletRequest) request).getRequestURI().startsWith("/swagger-ui")
                || ((HttpServletRequest) request).getRequestURI().startsWith("/v3/api-docs")
                || ((HttpServletRequest) request).getRequestURI().startsWith("/swagger-resources")) {

            chain.doFilter(request, response);
            return;
        }
        String header = req.getHeader("X-API-KEY");

        if (header == null || !header.equals(apiKey)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }
}
