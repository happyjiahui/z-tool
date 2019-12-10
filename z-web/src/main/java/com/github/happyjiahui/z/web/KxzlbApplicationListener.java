package com.github.happyjiahui.z.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author zhaojinbing
 * @version  2019/12/4 18:21
 */
public class KxzlbApplicationListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        SpringApplication springApplication = event.getSpringApplication();

        Map<String, Object> properties = new HashMap<>(16);
        // 设置控制台日志输出编码为UTF-8,防止日志乱码
        properties.put("logging.file", "classpath:logback-spring.xml");
        springApplication.setDefaultProperties(properties);
    }
}
