package com.github.happyjiahui.z.exception;

/**
 * 业务异常
 * 
 * @author zhaojinbing
 */
public class UtilException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UtilException() {
        super();
    }

    public UtilException(String s) {
        super(s);
    }

    public UtilException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UtilException(Throwable throwable) {
        super(throwable);
    }
}