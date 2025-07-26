package com.fw.bo.config;

import com.fw.bo.config.interceptor.BoMenuInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * BO WebMvc Config
 */
@Configuration
@RequiredArgsConstructor
public class BoWebMvcConfig implements WebMvcConfigurer {

    @Value("${service.upload.path}")
    private String SERVICE_UPLOAD_PATH;

    @Bean
    public BoMenuInterceptor boMenuInterceptor(){
        return new BoMenuInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + SERVICE_UPLOAD_PATH + "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(boMenuInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/bo/login/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/bo/logout/**");
    }

}
