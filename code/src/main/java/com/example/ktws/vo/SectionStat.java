package com.example.ktws.vo;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Map;

public class SectionStat {
    private Long id;

    private Long courseId;

    private Timestamp datetime;

    private JSONObject info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public JSONObject getInfo() {
        return info;
    }

    public void setInfo(JSONObject info) {
        this.info = info;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
