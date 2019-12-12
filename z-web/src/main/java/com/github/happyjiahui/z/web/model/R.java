package com.github.happyjiahui.z.web.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

/**
 * spring web 统一响应
 * 
 * @author zhaojinbing
 */
public class R extends HashMap<String, Object> {

    private R() {

    }

    private static void handlerR(R r, Object... data) {
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("传入的参数必须是偶数");
        }
        for (int i = 0; i <= data.length - 2; i = i + 2) {
            if (data[i] instanceof String) {
                r.put(String.valueOf(data[i]), data[i + 1]);
            }
        }
    }

    /**
     * 返回成功响应，success标识为true
     * 
     * @return {@link R}
     */
    public static R ok() {
        R r = new R();
        r.put("success", true);
        return r;
    }

    /**
     * 返回成功响应，success标识为true
     * 
     * @param data
     *            响应请求时需要携带的内容，必须成对出现
     * @return {@link R}
     */
    public static R ok(Object... data) {
        R r = R.ok();
        handlerR(r, data);
        return r;
    }

    /**
     * 返回成功响应，success标识为true
     *
     * @param map
     *            响应请求时需要携带的内容
     * @return {@link R}
     */
    public static R ok(Map<String, Object> map) {
        R r = R.ok();
        map.forEach(r::put);
        return r;
    }

    /**
     * 返回失败响应，success标识为false
     * 
     * @return {@link R}
     */
    public static R error() {
        R r = new R();
        r.put("success", false);
        return r;
    }

    /**
     * 返回失败响应，success标识为false
     * 
     * @param message
     *            失败消息
     * @return {@link R}
     */
    public static R error(String message) {
        R r = R.error();
        r.put("message", message);
        return r;
    }

    /**
     * 返回失败响应，success标识为false
     * 
     * @param message
     *            失败消息
     * @param data
     *            响应请求时需要携带的内容
     * @return {@link R}
     */
    public static R error(String message, Object... data) {
        R r = R.error(message);
        handlerR(r, data);
        return r;
    }

    /**
     * 从响应中获取success标识
     * 
     * @return {@link Boolean}
     */
    public Boolean success() {
        String success = this.getOrDefault("success", "false").toString();
        return Boolean.valueOf(success);
    }

    /**
     * 从响应中获取失败消息
     * 
     * @return 失败消息
     */
    public String getErrorMessage() {
        return this.getOrDefault("message", "").toString();
    }

    /**
     * 从响应中获取指定值
     * 
     * @param key
     *            键值
     * @param clazz
     *            value的class
     * @param <T>
     *            value的类型
     * @return value
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> clazz) {
        Object value = this.get(key);
        if (value == null) {
            return null;
        }
        return (T)ConvertUtils.convert(value, clazz);
    }

}