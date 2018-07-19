package com.example.ktws.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestSenderImpl implements RequestSender{

    @Autowired
    private AmqpTemplate rabbiTemplate;

    @Override
    public void send(Object o,String queueName){
        System.out.println("Sender sending to " + queueName);
        this.rabbiTemplate.convertAndSend(queueName, o);
    }
}
