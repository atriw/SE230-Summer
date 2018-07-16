package com.example.ktws.service;

import com.example.ktws.domain.TimeSlot;
import com.example.ktws.util.Day;

import java.util.Optional;

public interface TimeSlotService {
    TimeSlot addNewTimeSlot(Day day, String startTime, String endTime);

    Optional<TimeSlot> findByDayAndStartTimeAndEndTime(Day day, String startTime, String endTime);
}
