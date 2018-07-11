package com.example.demo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestSenderImpl implements RequestSender{

    @Autowired
    private AmqpTemplate rabbiTemplate;

    @Override
    public void send(Object o,String queueName){
        this.rabbiTemplate.convertAndSend(queueName,o);
    }
}
