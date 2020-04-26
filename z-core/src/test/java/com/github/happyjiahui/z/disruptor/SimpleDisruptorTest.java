package com.github.happyjiahui.z.disruptor;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleDisruptorTest {

    @Test
    public void doTest() {

        List<PersonMessage> lists = new ArrayList<>();

        SimpleDisruptor<PersonMessage> simpleDisruptor = new SimpleDisruptor<>(32, new IEventHandler<PersonMessage>() {

            @Override
            public void handle(PersonMessage body) {
                lists.add(body);
            }
        });
        int size = 1000;
        for (int i = 0; i < size; i++) {
            PersonMessage message = new PersonMessage();
            message.setId("id_" + i);
            message.setAge(i + 1);
            message.setName("name_" + i);
            simpleDisruptor.send(message);
        }

        Assert.assertEquals(lists.size(), size);

    }

}
