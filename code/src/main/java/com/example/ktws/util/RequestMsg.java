package com.example.ktws.util;

public class RequestMsg {

    private Long sectionId;

    private String camera;

    private Integer interval;

    private Integer duration;

    public Long getSectionId() {
        return sectionId;
    }

    public String getCamera() {
        return camera;
    }

    public Integer getInterval(){
        return interval;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
