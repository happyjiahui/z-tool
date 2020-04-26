package com.github.happyjiahui.z.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * @param <T>
 * @author zhaojinbing
 */
public class SimpleDisruptor<T> {

    private int bufferSize;

    private IEventHandler<T> eventHandler;

    private Disruptor<MessageEvent<T>> disruptor;

    public SimpleDisruptor(int bufferSize, IEventHandler<T> eventHandler) {
        this.bufferSize = bufferSize;
        this.eventHandler = eventHandler;
        init();
    }

    private void init() {
        Disruptor<MessageEvent<T>> disruptor = new Disruptor<>(MessageEvent::new,
                this.bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new YieldingWaitStrategy());
        disruptor.handleEventsWith(new MessageEventHandler<T>(this.eventHandler));
        disruptor.start();
        this.disruptor = disruptor;
    }

    public void send(T message) {
        RingBuffer<MessageEvent<T>> ringBuffer = this.disruptor.getRingBuffer();
        ringBuffer.publishEvent(new MessageEventTranslator<>(), message);
    }

    public void shutdown() {
        if (this.disruptor != null) {
            this.disruptor.shutdown();
        }
    }

}
