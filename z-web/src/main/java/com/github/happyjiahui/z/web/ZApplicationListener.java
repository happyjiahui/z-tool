package com.github.happyjiahui.z.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring事件 容器启动之前触发
 * 
 * @author zhaojinbing
 * @version 0.1
 */
public class ZApplicationListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        SpringApplication springApplication = event.getSpringApplication();

        Map<String, Object> properties = new HashMap<>(16);
        // 设置控制台日志输出编码为UTF-8,防止日志乱码
        properties.put("server.port", 10009);
        properties.put("logging.file", "classpath:logback-spring.xml");
        springApplication.setDefaultProperties(properties);
    }
}
