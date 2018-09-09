package com.example.ktws.util;

import java.util.Objects;

public class SpecificTime {
    // e.g. Range:1-7, 1 represents Sunday, 2 represents Monday
    private Day day;
    // e.g. using format like 08:00 , 13:50 etc.
    private String startTime;
    private String endTime;

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SpecificTime) {
            SpecificTime specificTime = (SpecificTime) obj;
            return Objects.equals(day, specificTime.getDay()) &&
                    startTime.equals(specificTime.getStartTime())&&
                    endTime.equals(specificTime.getEndTime());
        }
        return false;
    }
}
