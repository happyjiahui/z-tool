package com.github.happyjiahui.z.disruptor;

/**
 * 消息事件
 *
 * @author zhaojinbing
 */
public class MessageEvent<T> {

    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


}
