package com.example.ktws.mq;

public interface RequestSender {

    void send(Object o, String queueName);
}
