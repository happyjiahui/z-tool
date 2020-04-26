package com.github.happyjiahui.z.disruptor;

public interface IEventHandler<T> {

    void handle(T body);

}
