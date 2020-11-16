package com.github.happyjiahui.z.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.happyjiahui.z.web.interceptor.LogInterceptor;
import com.github.happyjiahui.z.web.service.ISendLogService;

/**
 * spring自动配置
 * 
 * @author zhaojinbing
 * @version 0.1
 */
@Configuration
@ComponentScan("com.github.happyjiahui.z.web.response")
public class SelfAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private ISendLogService sendLogService;
    @Value("${z.web.enable-log-interceptor:false}")
    private Boolean enableLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截
        if (enableLogInterceptor) {
            registry.addInterceptor(new LogInterceptor(sendLogService));
        }
    }
}
