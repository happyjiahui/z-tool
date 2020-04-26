package com.github.happyjiahui.z.disruptor;

/**
 * 事件处理接口
 *
 * @param <T>
 * @author zhaojinbing
 */
public interface IEventHandler<T> {

    void handle(T body);

}
