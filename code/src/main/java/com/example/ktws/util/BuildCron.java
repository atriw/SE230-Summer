package com.example.ktws.util;

public class BuildCron {
    public String generate(String startTime, Day day){
        String cron;
        cron = "0 " + Integer.valueOf(startTime.substring(3,5)) + " " + Integer.valueOf(startTime.substring(0,2)) + " ? * ";
        switch (day){
            case SUN:{
                cron = cron + "SUN";
                break;
            }
            case MON:{
                cron = cron + "MON";
                break;
            }
            case TUE:{
                cron = cron + "TUE";
                break;
            }
            case WED:{
                cron = cron + "WED";
                break;
            }
            case THU:{
                cron = cron + "THU";
                break;
            }
            case FRI:{
                cron = cron + "FRI";
                break;
            }
            case SAT:{
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
