package com.github.happyjiahui.z.web.service;

/**
 * 发送日志服务
 * 
 * @author zhaojinbing
 * @version 0.1
 */
public interface ISendLogService {

    /**
     * 发送日志
     * 
     * @param log
     *            日志内容
     */
    void sendLog(String log);

}
