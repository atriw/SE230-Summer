package com.example.ktws.util;

public class BuildCron {
    public String generate(String startTime, int day){
        String cron;
        cron = "0 " + Integer.valueOf(startTime.substring(3,5)) + " " + Integer.valueOf(startTime.substring(0,2)) + " ? * ";
        switch (day){
            case (1):{
                cron = cron + "SUN";
                break;
            }
            case (2):{
                cron = cron + "MON";
                break;
            }
            case (3):{
                cron = cron + "TUE";
                break;
            }
            case (4):{
                cron = cron + "WED";
                break;
            }
            case (5):{
                cron = cron + "THU";
                break;
            }
            case (6):{
                cron = cron + "FRI";
                break;
            }
            case (7):{
                cron = cron + "SAT";
                break;
            }
            default:{
                System.out.println("day must be in range 1~7!");
                return null;
            }
        }
        return cron;
    }

    public int getDuration(String startTime, String endTime){
        int hour = Integer.valueOf(endTime.substring(0,2)) - Integer.valueOf(startTime.substring(0,2));
        int minute = Integer.valueOf(endTime.substring(3,5)) - Integer.valueOf(startTime.substring(3,5));
        return hour*60 + minute;
    }
}
