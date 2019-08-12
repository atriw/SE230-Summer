package com.example.ktws.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestSenderImpl implements RequestSender{

    @Autowired
    private AmqpTemplate rabbiTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
    public void send(Object o,String queueName){
        logger.info("Sender sending to queue [name={}]", queueName);
        this.rabbiTemplate.convertAndSend(queueName, o);
    }
}
