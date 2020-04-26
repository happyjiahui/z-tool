package com.github.happyjiahui.z.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author zhaojinbing
 */
public class MessageEventFactory<T> implements EventFactory<MessageEvent<T>> {
    @Override
    public MessageEvent<T> newInstance() {
        return new MessageEvent<>();
    }
}
