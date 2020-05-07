package com.example.demo;

import com.example.demo.interceptor.AuthenticateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: WebConfigurer
 * @Description: 配置类
 * @Author: 刘敬
 * @Date: 2019/6/9 20:46
 **/
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<String>();
        list.add("/doctor/*");
        list.add("/patient/*");
        list.add("/insurer/*");
        list.add("/research/*");
        list.add("/bank/*");
        registry.addInterceptor(AuthenticateInterceptor()).addPathPatterns(list);
    }

    @Bean
    public AuthenticateInterceptor AuthenticateInterceptor() {
        return new AuthenticateInterceptor();
    }
}
