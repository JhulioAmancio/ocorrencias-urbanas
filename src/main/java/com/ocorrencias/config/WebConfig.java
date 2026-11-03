package com.ocorrencias.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/ocorrencias/**", "/ocorrencias");
    }

    // permite abrir as fotos salvas na pasta uploads
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String pasta = "file:" + Paths.get("uploads").toAbsolutePath() + "/";
        registry.addResourceHandler("/uploads/**").addResourceLocations(pasta);
    }
}
