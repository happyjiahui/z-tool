package com.github.happyjiahui.z.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * web 统一响应
 *
 * @author zhaojinbing
 */
@ApiModel("统一响应")
public class ResponseResult<T> {

    @ApiModelProperty("响应码")
    private int code;
    @ApiModelProperty("响应消息")
    private String message;
    @ApiModelProperty("响应数据")
    private T data;
    @ApiModelProperty("响应时间戳")
    private long timestamp;

    private ResponseResult() {

    }

    private ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    private ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }


    public static <T> ResponseResult<T> SUCCESS(T data) {
        return new ResponseResult<T>(200, "success", data);
    }

    public static <T> ResponseResult<T> SUCCESS() {
        return new ResponseResult<T>(200, "success");
    }

    protected static <T> ResponseResult<T> FAIL(int code, String message) {
        return new ResponseResult<T>(code, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
