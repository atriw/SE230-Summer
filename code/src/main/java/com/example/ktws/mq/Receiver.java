package com.example.ktws.mq;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.SectionService;
import com.example.ktws.service.StatService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        logger.info("RECEIVE:" + message);
        JSONObject jsonObject = new JSONObject(message);

        boolean success = jsonObject.getBoolean("success");
        if (success) {
            processInfo(jsonObject);
        } else {
            processError(jsonObject);
        }
    }

    private void processInfo(JSONObject jsonObject) {
        JSONObject statInfo = jsonObject.getJSONObject("info");
        Long sectionId = jsonObject.getLong("sectionId");
        Long timestamp = jsonObject.getLong("timestamp");
        String imgUrl = jsonObject.getString("url");

        Optional<Section> s = sectionService.findById(sectionId);
        if (!s.isPresent()) {
            logger.error("No such section [id={}]", sectionId);
            return;
        }
        Section section = s.get();
        Photo photo = photoService.addNewPhoto(timestamp, section, imgUrl);
        statService.parseAndAddStatInfo(statInfo, photo);
        logger.info("ReceiveInfo: Stored photo {} and statInfo", photo);
    }

    private void processError(JSONObject jsonObject) {
        String errorMsg = jsonObject.getString("errorMsg");
        logger.error(errorMsg);
    }
}
