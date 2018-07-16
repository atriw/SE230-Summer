package com.example.ktws.service.Impl;

import com.example.ktws.domain.TimeSlot;
import com.example.ktws.repository.TimeSlotRepository;
import com.example.ktws.service.TimeSlotService;
import com.example.ktws.util.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Override
    public TimeSlot addNewTimeSlot(Day day, String startTime, String endTime) {
        Optional<TimeSlot> ots = findByDayAndStartTimeAndEndTime(day, startTime, endTime);
        if (ots.isPresent()) {
            return ots.get();
        }
        TimeSlot ts = new TimeSlot();
        ts.setDay(day);
        ts.setStartTime(startTime);
        ts.setEndTime(endTime);
        return timeSlotRepository.save(ts);
    }

    @Override
    public Optional<TimeSlot> findByDayAndStartTimeAndEndTime(Day day, String startTime, String endTime) {
        return timeSlotRepository.findByDayAndStartTimeAndEndTime(day, startTime, endTime);
    }
}
