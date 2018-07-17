package com.example.ktws.service.Impl;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.TimeSlot;
import com.example.ktws.domain.User;
import com.example.ktws.repository.CourseRepository;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.ScheduleService;
import com.example.ktws.service.TimeSlotService;
import com.example.ktws.util.BuildCron;
import com.example.ktws.util.Day;
import com.example.ktws.util.SpecificTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TimeSlotService timeSlotService;

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public Iterable<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Iterable<Course> getCoursesByUser(User user){
        return courseRepository.findByUser(user);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course addNewCourse(Course c, List<SpecificTime> time){
        Optional<Course> oldCourse = courseRepository.findByName(c.getName());
        if (oldCourse.isPresent()) {
            return oldCourse.get();
        }
        addTimeSlotsToCourse(time, c);
        courseRepository.save(c);

        List<String> cronExpressions = new ArrayList<>();
        BuildCron buildCron = new BuildCron();
        Integer duration = 7200;
        for (SpecificTime t : time) {
            String cron = buildCron.generate(t.getStartTime(), t.getDay());
            cronExpressions.add(cron);
            duration = buildCron.getDuration(t.getStartTime(), t.getEndTime());
        }
        try {
            scheduleService.add(c.getId(), c.getCamera(), c.getInterval(), cronExpressions, duration);
        } catch (Exception e) {
            System.out.println("ERROR: schedule failed");
        }

        return c;
    }

    @Override
    public boolean deleteCourse(Long id) {
        Optional<Course> c = courseRepository.findById(id);
        if (!c.isPresent()) {
            return false;
        }
        Course course = c.get();
        removeAllTimeSlotsOfCourse(course);
        courseRepository.delete(course);
        try {
            scheduleService.delete(course.getId());
        } catch (Exception e) {
            System.out.println("ERROR: job deletion failed");
        }
        return true;
    }

    @Override
    public boolean updateCourse(String oldName, String name, String address, String camera, Integer numOfStudent, Integer interval, List<SpecificTime> time) {
        //TODO: buggy, name会变为null
        Optional<Course> oc = courseRepository.findByName(oldName);
        if (!oc.isPresent()) {
            return false;
        }
        Course c = oc.get();
        c.setName(name);
        c.setAddress(address);
        c.setCamera(camera);
        c.setNumOfStudent(numOfStudent);
        c.setInterval(interval);

        // TODO: 愚蠢的更新方法
        removeAllTimeSlotsOfCourse(c);
        addTimeSlotsToCourse(time, c);
        courseRepository.save(c);

        List<String> cronExpressions = new ArrayList<>();
        BuildCron buildCron = new BuildCron();
        Integer duration = 7200;
        for (SpecificTime t : time) {
            String cron = buildCron.generate(t.getStartTime(), t.getDay());
            cronExpressions.add(cron);
            duration = buildCron.getDuration(t.getStartTime(), t.getEndTime());
        }
        try {
            scheduleService.modify(c.getId(), camera, interval, cronExpressions, duration);
        } catch (Exception e) {
            System.out.println("ERROR: schedule modification failed");
        }
        return true;
    }

    private void addTimeSlotsToCourse(List<SpecificTime> time, Course c) {
        for (SpecificTime t : time) {
            Day day = t.getDay();
            String startTime = t.getStartTime();
            String endTime = t.getEndTime();
            Optional<TimeSlot> ots = timeSlotService.findByDayAndStartTimeAndEndTime(day, startTime, endTime);
            if (ots.isPresent()) {
                c.addTimeSlot(ots.get());
            } else {
                TimeSlot ts = timeSlotService.addNewTimeSlot(day, startTime, endTime);
                c.addTimeSlot(ts);
            }
        }
    }

    private void removeAllTimeSlotsOfCourse(Course c) {
        Set<TimeSlot> timeSlots = c.getTimeSlots();
        ArrayList<TimeSlot> tsList = new ArrayList<>(timeSlots);
        for (TimeSlot aTsList : tsList) {
            c.removeTimeSlot(aTsList);
        }
//        for (TimeSlot ts : timeSlots) {
//            c.removeTimeSlot(ts);
//        }
    }
}
