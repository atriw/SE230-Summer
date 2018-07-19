package com.example.ktws.mq;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.SectionService;
import com.example.ktws.service.StatService;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @Autowired
    private PhotoService photoService;

    @Autowired
    private StatService statService;

    @Autowired
    private SectionService sectionService;

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

        JSONObject statInfo = jsonObject.getJSONObject("info");
        Long sectionId = jsonObject.getLong("sectionId");
        Long timestamp = jsonObject.getLong("timestamp");
        String imgUrl = jsonObject.getString("url");

        Optional<Section> s = sectionService.findById(sectionId);
        if (!s.isPresent()) {
            return;
        }
        Section section = s.get();
        Photo photo = photoService.addNewPhoto(timestamp, section, imgUrl);
        statService.parseAndAddStatInfo(statInfo, photo);
    }
}
