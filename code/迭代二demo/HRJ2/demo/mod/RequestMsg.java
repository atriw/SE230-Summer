package com.example.demo.mod;

public class RequestMsg {
    //课程id
    private long id;

    private String ip;

    private int interval;

    private int duration;

    public long getId() {
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

    public void setId(long id) {
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
