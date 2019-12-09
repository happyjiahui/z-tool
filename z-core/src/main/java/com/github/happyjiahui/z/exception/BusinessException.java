package com.github.happyjiahui.z.exception;

/**
 * 业务异常
 * 
 * @author zhaojinbing
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }
}