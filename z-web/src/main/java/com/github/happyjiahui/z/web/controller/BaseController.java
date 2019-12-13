package com.github.happyjiahui.z.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.github.happyjiahui.z.util.JsonUtils;

/**
 * controller基类，提供参数校验等功能。
 * 
 * @author zhaojinbing
 * @version 0.1
 */
public class BaseController {

    /**
     * 校验参数
     * 
     * @param result
     *            {@link BindingResult}
     */
    protected void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            List<Map<String, Object>> errorMapList = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("field", fieldError.getField());
                errorMap.put("message", fieldError.getDefaultMessage());
                errorMapList.add(errorMap);
            }
            throw new ValidationException(JsonUtils.toString(errorMapList));
        }
    }

}
