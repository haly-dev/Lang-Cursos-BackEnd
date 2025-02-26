package com.example.projetolangcursos.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Requisição recebida: {} {}", request.getMethod(), request.getRequestURI());
        logger.debug("Headers: {}", request.getHeaderNames());

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Erro no filtro de autorização: {}", e.getMessage(), e);
            throw e;
        }

        logger.debug("Resposta enviada: {}", response.getStatus());
    }
}