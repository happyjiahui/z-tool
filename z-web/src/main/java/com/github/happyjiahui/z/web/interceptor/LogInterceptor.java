package com.github.happyjiahui.z.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.happyjiahui.z.web.annotation.RecordLog;
import com.github.happyjiahui.z.web.model.SysLog;
import com.github.happyjiahui.z.web.service.ISendLogService;

/**
 * 日志拦截
 * 
 * @author zhaojinbing
 * @version 0.1
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);
    private NamedThreadLocal<SysLog> startTimeThreadLocal = new NamedThreadLocal<>("SysLog");
    private ISendLogService sendLogService;

    public LogInterceptor(ISendLogService sendLogService) {
        this.sendLogService = sendLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        if (!(o instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod)o).getMethod();
        String methodName = method.getDeclaringClass().getName() + "." + method.getName();
        RecordLog recordLog = method.getAnnotation(RecordLog.class);
        if (recordLog == null) {
            return true;
        }
        String logContent = recordLog.value();
        SysLog sysLog = new SysLog();
        sysLog.recordLog(httpServletRequest, methodName, logContent);
        startTimeThreadLocal.set(sysLog);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        Object o, Exception e) {
        if (!(o instanceof HandlerMethod)) {
            return;
        }
        Method method = ((HandlerMethod)o).getMethod();
        RecordLog recordLog = method.getAnnotation(RecordLog.class);
        if (recordLog == null) {
            return;
        }
        SysLog sysLog = startTimeThreadLocal.get();
        sysLog.setConsumeTime();
        startTimeThreadLocal.remove();
        if (sendLogService == null) {
            LOGGER.info(sysLog.toString());
        } else {
            sendLogService.sendLog(sysLog.toString());
        }
    }
}
