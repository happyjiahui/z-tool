package com.github.happyjiahui.z.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.alibaba.fastjson.JSON;

/**
 * @author zhaojinbing
 * @Classname BaseController
 * @Description TODO
 * @Date 2019/12/2 15:56
 */
public class BaseController {

    protected void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            List<Map<String, Object>> errorMapList = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("field", fieldError.getField());
                errorMap.put("message", fieldError.getDefaultMessage());
                errorMapList.add(errorMap);
            }
            throw new ValidationException(JSON.toJSONString(errorMapList));
        }
    }

}
