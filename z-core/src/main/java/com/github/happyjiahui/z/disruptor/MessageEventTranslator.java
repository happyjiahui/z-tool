package com.github.happyjiahui.z.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;

public class MessageEventTranslator<T> implements EventTranslatorOneArg<MessageEvent<T>, T> {
    @Override
    public void translateTo(MessageEvent<T> messageEvent, long l, T message) {
        messageEvent.setBody(message);
    }
}
