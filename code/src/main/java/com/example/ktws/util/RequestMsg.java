package com.example.ktws.util;

public class RequestMsg {
    //s_id
    private Long id;

    private String ip;

    private int interval;

    private int duration;

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public int getInterval(){
        return interval;
    }

    public int getDuration() {
        return duration;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
