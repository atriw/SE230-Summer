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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatRepository statRepository;

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
        return statRepository.save(stat);
    }

    /**
     *
     * @param statInfo format: {} //TODO: 根据facepp返回结果修改
     * @param photo the photo which the statInfo is associated with
     * @return boolean indicate whether the parsing and insertion are successful
     */
    @Override
    public boolean parseStatInfo(JSONArray statInfo, Photo photo) {
        return false;
    }

    @Override
    public Iterable<Stat> getStatsByLastCourse(User user) { //TODO: 未完成
        return null;
    }

    @Override
    public Iterable<Stat> getStatsByLast3Courses(User u) {
        return null;
    }

    @Override
    public Iterable<Stat> getStatsByCourse(Course course) {
        return null;
    }
}
