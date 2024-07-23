package com.udemy.security.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Slf4j
public class AuthoritieslLoggingAfterFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication){
            log.info("User:" + authentication.getName() + " is successfully authenticated and has the authorites: " + authentication.getAuthorities().toString());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
