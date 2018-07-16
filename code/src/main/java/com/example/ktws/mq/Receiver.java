package com.example.ktws.mq;

import java.io.UnsupportedEncodingException;

import com.example.ktws.service.PhotoService;
import com.example.ktws.service.StatService;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver { // TODO: 图片和info用什么来标识？
    @Autowired
    private PhotoService photoService;

    @Autowired
    private StatService statService;

    static final private String INFO_QUEUE_NAME = "infoQueue";

//    static final private String PICTURE_QUEUE_NAME = "pictureQueue";

//    @RabbitHandler
//    @RabbitListener(queues = PICTURE_QUEUE_NAME)
//    public void receivePicture(byte[] body) throws UnsupportedEncodingException {
//        String message = new String(body,"UTF-8");
//        System.out.println(message);
//        JSONObject jsonObject = new JSONObject(message);
//    }

    @RabbitHandler
    @RabbitListener(queues = INFO_QUEUE_NAME)
    public void receiveInfo(byte[] body) throws UnsupportedEncodingException {
        String message = new String(body,"UTF-8");
        System.out.println("RECEIVE:" + message);
        JSONObject jsonObject = new JSONObject(message);
        // TODO: 根据facepp返回结果进行解析
    }
}
