package com.example.demo;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    static final private String INFO_QUEUE_NAME = "infoQueue";

    static final private String PICTURE_QUEUE_NAME = "pictureQueue";

    @RabbitHandler
    @RabbitListener(queues = PICTURE_QUEUE_NAME)
    public void receivePicture(byte[] body) throws UnsupportedEncodingException {
        String message =new String(body,"UTF-8");
        System.out.println(message);
        JSONObject jsonObject = new JSONObject(message);
        System.out.println(jsonObject.get("a"));
    }

    @RabbitHandler
    @RabbitListener(queues = INFO_QUEUE_NAME)
    public void receiveInfo(byte[] body) throws UnsupportedEncodingException {
        String message =new String(body,"UTF-8");
        System.out.println(message);
        JSONObject jsonObject = new JSONObject(message);
        System.out.println(jsonObject.get("a"));
    }
}
//@Component
//public class Receiver {
//
//    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
//    public void consumeMessage(String message) {
//        System.out.println("consume message {}"+ message);
//    }
//    private CountDownLatch latch = new CountDownLatch(1);

//    public void receiveMessage(String message) {
//        System.out.println("Received <" + message + ">");
//        latch.countDown();
//    }
//
//    public CountDownLatch getLatch() {
//        return latch;
//    }
//}
