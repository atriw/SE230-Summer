package com.example.ktws.service;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Stat;
import com.example.ktws.domain.User;
import com.example.ktws.util.TypeOfFace;
import org.json.JSONArray;
import org.json.JSONObject;

public interface StatService {
    Iterable<Stat> getStatsByPhoto(Photo photo);

    Stat addNewStat(Integer numOfFace, TypeOfFace type, Photo photo);

    boolean parseAndAddStatInfo(JSONObject statInfo, Photo photo);
}
