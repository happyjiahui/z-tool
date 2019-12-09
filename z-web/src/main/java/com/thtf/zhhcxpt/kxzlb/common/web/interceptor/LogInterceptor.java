package com.thtf.zhhcxpt.kxzlb.common.web.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.thtf.zhhcxpt.kxzlb.common.util.HttpUtils;
import com.thtf.zhhcxpt.kxzlb.common.web.annotation.RecordLog;
import com.thtf.zhhcxpt.kxzlb.common.web.model.SysLog;
import com.thtf.zhhcxpt.kxzlb.common.web.service.ISendLogService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;

/**
 * @author zhaojinbing
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
        RecordLog recordLog = method.getAnnotation(RecordLog.class);
        if (recordLog == null) {
            return true;
        }
        SysLog sysLog = new SysLog();
        sysLog.setId(IdUtil.simpleUUID());
        sysLog.setConsumeTime(System.currentTimeMillis());
        startTimeThreadLocal.set(sysLog);

        sysLog.setMethodName(method.getDeclaringClass().getName() + "." + method.getName());
        sysLog.setUserOption(recordLog.value());
        sysLog.setUrl(httpServletRequest.getServletPath());
        sysLog.setReqMethod(httpServletRequest.getMethod());
        Map<String, String[]> reqParameterMap = httpServletRequest.getParameterMap();
        sysLog.setReqParameter(JSON.toJSONString(reqParameterMap));
        sysLog.setIp(HttpUtils.getIpAddr(httpServletRequest));

        String username = httpServletRequest.getHeader("username");
        String userId = httpServletRequest.getHeader("userId");
        sysLog.setUsername(username);
        sysLog.setUserId(userId);
        sysLog.setUserAgent(httpServletRequest.getHeader("User-Agent"));
        sysLog.setReqTime(DateUtil.now());
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
        long endTime = System.currentTimeMillis();
        sysLog.setConsumeTime(endTime - sysLog.getConsumeTime());
        startTimeThreadLocal.remove();
        if (sendLogService == null) {
            LOGGER.info(JSON.toJSONString(sysLog));
        } else {
            sendLogService.sendLog(JSON.toJSONString(sysLog));
        }
    }
}
