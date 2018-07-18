package com.example.ktws.vo;

public class CourseInfo {
    private Long id;

    private String name;

    private String time;

    private Integer numOfStudent;

    private Integer interval;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getNumOfStudent() {
        return numOfStudent;
    }

    public void setNumOfStudent(Integer numOfStudent) {
        this.numOfStudent = numOfStudent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
