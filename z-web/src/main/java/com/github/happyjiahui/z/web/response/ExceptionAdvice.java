package com.github.happyjiahui.z.web.response;

import com.github.happyjiahui.z.web.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;

/**
 * spring web 异常统一管理
 *
 * @author zhaojinbing
 * @version 0.1
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler
    @ResponseBody
    public ResponseResult<String> handleError(Exception ex) {
        if (ex instanceof BusinessException) {
            // 处理业务异常
            return ResponseResult.FAIL(505, ex.getMessage());
        } else if (ex instanceof ValidationException) {
            return ResponseResult.FAIL(400, "数据校验失败");
        } else {
            // 处理系统异常
            LOGGER.error(ex.getMessage(), ex);
            return ResponseResult.FAIL(502, "系统内部错误");
        }
    }
}
