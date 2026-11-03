package com.ocorrencias.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

// bloqueia as paginas de ocorrencias para quem nao fez login
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("usuarioLogado") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
