package com.example.ktws.service.Impl;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Stat;
import com.example.ktws.repository.StatRepository;
import com.example.ktws.service.StatService;
import com.example.ktws.util.Emotion;
import com.example.ktws.util.TypeOfFace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatRepository statRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Iterable<Stat> getStatsByPhoto(Photo photo) {
        return statRepository.findByPhoto(photo);
    }

    @Override
    public Stat addNewStat(Integer numOfFace, TypeOfFace type, Photo photo) {
        Stat stat = new Stat();
        stat.setNumOfFace(numOfFace);
        stat.setType(type);
        stat.setPhoto(photo);
        statRepository.save(stat);
        logger.info("AddNewStat: Added stat {}", stat);
        return stat;
    }

    /**
     *
     * @param statInfo format: {
     *                 "time_used":num, "image_id":id, "request_id":id,
     *                 "faces": [
     *                 {
     *                 "attributes": {
     *                 "emotion": {
     *                 "sadness":num, "neutral":num, "disgust":num,
     *                 "anger":num, "surprise":num, "fear":num, "happiness":num
     *                 }},
     *                 "face_rectangle": {}, "face_token":token
     *                 },
     *                 {...}, ...]
     *                 }
     * @param photo the photo which the statInfo is associated with
     * @return boolean indicate whether the parsing and insertion are successful
     */
    @Override
    public boolean parseAndAddStatInfo(JSONObject statInfo, Photo photo) {
        JSONArray faces = statInfo.getJSONArray("faces");
        Integer numOfFace = faces.length();
        TypeOfFace typeOfFace = TypeOfFace.ALL; //TODO:进阶需求：根据情绪储存
        addNewStat(numOfFace, typeOfFace, photo);

        int sadness = 0;
        int neutral = 0;
        int disgust = 0;
        int anger = 0;
        int surprise = 0;
        int fear = 0;
        int happiness = 0;

        for (int i = 0; i < faces.length(); i++) {
            JSONObject face = faces.getJSONObject(i);
            JSONObject attributes = face.getJSONObject("attributes");
            Emotion emotion = new Emotion(attributes.getJSONObject("emotion"));//TODO:错误处理,待测试
            TypeOfFace max = emotion.getMaxType();
            sadness += (max == TypeOfFace.SADNESS? 1 : 0);
            neutral += (max == TypeOfFace.NEUTRAL? 1 : 0);
            disgust += (max == TypeOfFace.DISGUST? 1 : 0);
            anger += (max == TypeOfFace.ANGER? 1 : 0);
            surprise += (max == TypeOfFace.SURPRISE? 1 : 0);
            fear += (max == TypeOfFace.FEAR? 1 : 0);
            happiness += (max == TypeOfFace.HAPPINESS? 1 : 0);
        }

        if (sadness > 0) {
            addNewStat(sadness, TypeOfFace.SADNESS, photo);
        }
        if (neutral > 0) {
            addNewStat(neutral, TypeOfFace.NEUTRAL, photo);
        }
        if (disgust > 0) {
            addNewStat(disgust, TypeOfFace.DISGUST, photo);
        }
        if (anger > 0) {
            addNewStat(anger, TypeOfFace.ANGER, photo);
        }
        if (surprise > 0) {
            addNewStat(surprise, TypeOfFace.SURPRISE, photo);
        }
        if (fear > 0) {
            addNewStat(fear, TypeOfFace.FEAR, photo);
        }
        if (happiness > 0) {
            addNewStat(happiness, TypeOfFace.HAPPINESS, photo);
        }
        logger.info("ParseAndAddStatInfo: Parsed and added statInfo to photo [id={}]", photo.getId());
        return true;
    }
}
