package com.example.ktws.service.Impl;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Stat;
import com.example.ktws.domain.User;
import com.example.ktws.repository.StatRepository;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.StatService;
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
        logger.info("AddNewStat: Successfully added a new stat with pid {} statid {}", photo.getId(), stat.getId());
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
        logger.info("ParseAndAddStatInfo: Successfully parse and add statInfo to photo {}", photo.getId());
        return true;
    }
}
