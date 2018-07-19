package com.example.ktws.service.Impl;

import com.example.ktws.domain.TimeSlot;
import com.example.ktws.repository.TimeSlotRepository;
import com.example.ktws.service.TimeSlotService;
import com.example.ktws.util.Day;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public TimeSlot addNewTimeSlot(Day day, String startTime, String endTime) {
        Optional<TimeSlot> ots = findByDayAndStartTimeAndEndTime(day, startTime, endTime);
        if (ots.isPresent()) {
            logger.info("AddNewTimeSlot: TimeSlot {} {}-{} already exists", day.toString(), startTime, endTime);
            return ots.get();
        }
        TimeSlot ts = new TimeSlot();
        ts.setDay(day);
        ts.setStartTime(startTime);
        ts.setEndTime(endTime);
        timeSlotRepository.save(ts);
        logger.info("AddNewTimeSlot: Added timeSlot {}", ts);
        return ts;
    }

    @Override
    public Optional<TimeSlot> findByDayAndStartTimeAndEndTime(Day day, String startTime, String endTime) {
        return timeSlotRepository.findByDayAndStartTimeAndEndTime(day, startTime, endTime);
    }
}
