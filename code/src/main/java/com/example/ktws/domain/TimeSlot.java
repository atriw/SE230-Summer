package com.example.ktws.domain;

import javax.persistence.*;

@Entity
@Table(name = "time_slot")
public class TimeSlot { //TODO：关联course
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String startTime;
    private String endTime;
    private String day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
