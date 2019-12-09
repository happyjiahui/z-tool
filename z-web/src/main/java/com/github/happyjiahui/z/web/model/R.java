package com.github.happyjiahui.z.web.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import cn.hutool.core.convert.Convert;

/**
 * @author zhaojinbing
 */
public class R extends HashMap<String, Object> {

    private R() {

    }

    private static void handlerR(R r, Object... data) {
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("传入的参数必须是偶数！");
        }
        for (int i = 0; i <= data.length - 2; i = i + 2) {
            if (data[i] instanceof String) {
                r.put(String.valueOf(data[i]), data[i + 1]);
            }
        }
    }

    public static R ok() {
        R r = new R();
        r.put("success", true);
        return r;
    }

    public static R ok(Object... data) {
        R r = R.ok();
        handlerR(r, data);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = R.ok();
        map.forEach(r::put);
        return r;
    }

    public static R error() {
        R r = new R();
        r.put("success", false);
        return r;
    }

    public static R error(String message) {
        R r = R.error();
        r.put("message", message);
        return r;
    }

    public static R error(String message, Object... data) {
        R r = R.error(message);
        handlerR(r, data);
        return r;
    }

    public static R page(Object pageObj) {
        Page<?> page = (Page<?>)pageObj;
        return R.ok("list", page.getContent(), "currentPage", page.getNumber(), "totalPage", page.getTotalPages(),
            "total", page.getTotalElements());
    }

    /**
     * 从r中获取success标识
     * 
     * @return
     */
    public Boolean success() {
        String success = this.getOrDefault("success", "false").toString();
        return Boolean.valueOf(success);
    }

    /**
     * 从r中获取指定值
     * 
     * @param key
     *            键值
     * @param clazz
     *            value的类型
     * @param <T>
     * @return
     */
    public <T> T getValue(String key, Class<T> clazz) {
        Object value = this.get(key);
        if (value == null) {
            return null;
        }

        return Convert.convert(clazz, value);
    }

}