package com.example.ktws.repository;

import com.example.ktws.domain.TimeSlot;
import com.example.ktws.util.Day;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
    Optional<TimeSlot> findByDayAndStartTimeAndEndTime(Day day, String startTime, String endTime);
}
