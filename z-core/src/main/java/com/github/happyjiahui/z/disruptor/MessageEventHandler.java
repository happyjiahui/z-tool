package com.github.happyjiahui.z.disruptor;

import com.lmax.disruptor.EventHandler;

public class MessageEventHandler<T> implements EventHandler<MessageEvent<T>> {

    private IEventHandler<T> eventHandler;

    public MessageEventHandler(IEventHandler<T> eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void onEvent(MessageEvent<T> messageEvent, long l, boolean b) throws Exception {
        T body = messageEvent.getBody();
        eventHandler.handle(body);
    }
}
