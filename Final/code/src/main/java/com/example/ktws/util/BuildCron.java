package com.example.ktws.util;

public class BuildCron {

    public String generate(String startTime, Day day){
        String cron;
        cron = "0 " + Integer.valueOf(startTime.substring(3,5)) + " " + Integer.valueOf(startTime.substring(0,2)) + " ? * ";
        cron += day.toString();
        return cron;
    }

    public int getDuration(String startTime, String endTime){
        int hour = Integer.valueOf(endTime.substring(0,2)) - Integer.valueOf(startTime.substring(0,2));
        int minute = Integer.valueOf(endTime.substring(3,5)) - Integer.valueOf(startTime.substring(3,5));
        return (hour * 60 + minute) * 60;
    }
}
