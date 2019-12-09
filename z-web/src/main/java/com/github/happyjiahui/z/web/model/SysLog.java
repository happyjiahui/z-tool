package com.github.happyjiahui.z.web.model;

import java.io.Serializable;

/**
 * @author zhaojinbing
 */
public class SysLog implements Serializable {
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
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取操作用户id
     *
     * @return user_id - 操作用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置操作用户id
     *
     * @param userId
     *            操作用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取操作用户
     *
     * @return username - 操作用户
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置操作用户
     *
     * @param username
     *            操作用户
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取操作标识
     *
     * @return user_option - 操作标识
     */
    public String getUserOption() {
        return userOption;
    }

    /**
     * 设置操作标识
     *
     * @param userOption
     *            操作标识
     */
    public void setUserOption(String userOption) {
        this.userOption = userOption;
    }

    /**
     * 获取方法
     *
     * @return method_name - 方法
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置方法
     *
     * @param methodName
     *            方法
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 获取请求地址
     *
     * @return url - 请求地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置请求地址
     *
     * @param url
     *            请求地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取请求方式
     *
     * @return req_method - 请求方式
     */
    public String getReqMethod() {
        return reqMethod;
    }

    /**
     * 设置请求方式
     *
     * @param reqMethod
     *            请求方式
     */
    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    /**
     * 获取请求参数
     *
     * @return req_parameter - 请求参数
     */
    public String getReqParameter() {
        return reqParameter;
    }

    /**
     * 设置请求参数
     *
     * @param reqParameter
     *            请求参数
     */
    public void setReqParameter(String reqParameter) {
        this.reqParameter = reqParameter;
    }

    /**
     * 获取访问ip
     *
     * @return ip - 访问ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置访问ip
     *
     * @param ip
     *            访问ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取用户标识
     *
     * @return user_agent - 用户标识
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置用户标识
     *
     * @param userAgent
     *            用户标识
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取请求耗时
     *
     * @return consume_time - 请求耗时
     */
    public Long getConsumeTime() {
        return consumeTime;
    }

    /**
     * 设置请求耗时
     *
     * @param consumeTime
     *            请求耗时
     */
    public void setConsumeTime(Long consumeTime) {
        this.consumeTime = consumeTime;
    }

    /**
     * 获取请求时间
     *
     * @return req_time - 请求时间
     */
    public String getReqTime() {
        return reqTime;
    }

    /**
     * 设置请求时间
     *
     * @param reqTime
     *            请求时间
     */
    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    @Override
    public String toString() {
        return "SysLog{" + "id='" + id + '\'' + ", userId='" + userId + '\'' + ", username='" + username + '\''
            + ", userOption='" + userOption + '\'' + ", methodName='" + methodName + '\'' + ", url='" + url + '\''
            + ", reqMethod='" + reqMethod + '\'' + ", reqParameter='" + reqParameter + '\'' + ", ip='" + ip + '\''
            + ", userAgent='" + userAgent + '\'' + ", consumeTime=" + consumeTime + ", reqTime=" + reqTime + '}';
    }
}