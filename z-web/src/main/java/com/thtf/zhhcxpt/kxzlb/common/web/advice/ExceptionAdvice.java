package com.thtf.zhhcxpt.kxzlb.common.web.advice;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thtf.zhhcxpt.kxzlb.common.exception.BusinessException;
import com.thtf.zhhcxpt.kxzlb.common.web.model.R;

/**
 * @author zhaojinbing
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler
    @ResponseBody
    public R handleError(Exception ex) {
        if (ex instanceof BusinessException) {
            // 处理业务异常
            return R.error(ex.getMessage());
        } else if (ex instanceof ValidationException) {
            return R.error("数据校验错误", "detail", ex.getMessage());
        } else {
            // 处理系统异常
            LOGGER.error(ex.getMessage(), ex);
            return R.error("系统内部错误");
        }
    }
}
