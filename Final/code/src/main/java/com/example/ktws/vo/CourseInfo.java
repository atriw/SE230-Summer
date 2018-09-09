package com.example.ktws.vo;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.TimeSlot;

import java.util.Set;

public class CourseInfo {
    private Long id;

    private String name;

    private String time;

    private String address;

    private Integer numOfStudent;

    private Integer interval;

    private String camera;

    public CourseInfo() {}

    public CourseInfo(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.time = buildTime(course.getTimeSlots());
        this.numOfStudent = course.getNumOfStudent();
        this.interval = course.getInterval();
        this.camera = course.getCamera();
        this.address = course.getAddress();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

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

    private String buildTime(Set<TimeSlot> timeSlots) {
        StringBuilder time = new StringBuilder();
        for (TimeSlot timeSlot: timeSlots) {
            time.append(timeSlot.getDay().toString());
            time.append(" ");
            time.append(timeSlot.getStartTime());
            time.append("-");
            time.append(timeSlot.getEndTime());
            time.append("\n");
        }
        return time.toString();
    }
}
