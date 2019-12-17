package com.github.happyjiahui.z.web.model;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.happyjiahui.z.util.DateTimeUtils;
import com.github.happyjiahui.z.util.HttpUtils;
import com.github.happyjiahui.z.util.IdUtils;
import com.github.happyjiahui.z.util.JsonUtils;

/**
 * @author zhaojinbing
 * @version 0.1
 */
public class SysLog implements Serializable {

    /**
     * 日志id
     */
    private String id;

    /**
     * 操作用户id
     */
    private String userId;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作标识
     */
    private String userOption;

    /**
     * 方法
     */
    private String methodName;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求方式
     */
    private String reqMethod;

    /**
     * 请求参数
     */
    private String reqParameter;

    /**
     * 访问ip
     */
    private String ip;

    /**
     * 用户标识
     */
    private String userAgent;

    /**
     * 请求耗时
     */
    private Long consumeTime;

    /**
     * 请求时间
     */
    private String reqTime;

    /**
     * 记录日志
     * 
     * @param httpServletRequest
     *            servlet请求
     * @param methodName
     *            方法名称
     * @param logContent
     *            日志内容
     * @return {@link SysLog}
     */
    public SysLog recordLog(HttpServletRequest httpServletRequest, String methodName, String logContent) {
        this.id = IdUtils.simpleUUID();
        this.consumeTime = System.currentTimeMillis();
        this.methodName = methodName;
        this.userOption = logContent;
        this.url = httpServletRequest.getServletPath();
        this.reqMethod = httpServletRequest.getMethod();
        Map<String, String[]> reqParameterMap = httpServletRequest.getParameterMap();
        this.reqParameter = JsonUtils.toString(reqParameterMap);
        this.ip = HttpUtils.getIpAddr(httpServletRequest);
        this.userAgent = httpServletRequest.getHeader("User-Agent");
        this.reqTime = DateTimeUtils.now();

        String username = httpServletRequest.getHeader("username");
        String userId = httpServletRequest.getHeader("userId");
        this.username = username;
        this.userId = userId;

        return this;
    }

    public void setConsumeTime() {
        long endTime = System.currentTimeMillis();
        this.consumeTime = endTime - this.consumeTime;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserOption() {
        return userOption;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getUrl() {
        return url;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public String getReqParameter() {
        return reqParameter;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Long getConsumeTime() {
        return consumeTime;
    }

    public String getReqTime() {
        return reqTime;
    }

    @Override
    public String toString() {
        return JsonUtils.toString(this);
    }
}