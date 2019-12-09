package com.github.happyjiahui.z.web;

import com.github.happyjiahui.z.web.interceptor.LogInterceptor;
import com.github.happyjiahui.z.web.service.ISendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhaojinbing
 * @Classname SelfAutoConfig
 * @Description TODO
 * @Date 2019/12/2 17:16
 */
@Configuration
@ComponentScan("com.thtf.zhhcxpt.kxzlb.common.web.advice")
public class SelfAutoConfig implements WebMvcConfigurer {

    private final ISendLogService sendLogService;
    @Value("${kxzlb.web.enable-log-interceptor:false}")
    private Boolean enableLogInterceptor;

    @Autowired(required = false)
    public SelfAutoConfig(ISendLogService sendLogService) {
        this.sendLogService = sendLogService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截
        if (enableLogInterceptor) {
            registry.addInterceptor(new LogInterceptor(sendLogService));
        }
    }
}
