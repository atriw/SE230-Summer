package com.example.ktws.service.Impl;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.TimeSlot;
import com.example.ktws.domain.User;
import com.example.ktws.repository.CourseRepository;
import com.example.ktws.service.CourseService;
import com.example.ktws.util.SpecificTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Iterable<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Iterable<Course> getCoursesByUser(User user){
        Long user_id = user.getId();
        return courseRepository.findByUser_Id(user_id);
    }

    @Override
    public boolean addNewCourse(String name, String address, String camera, Integer numOfStudent, Integer interval, List<SpecificTime> time, User user){
        Optional<Course> oldCourse = courseRepository.findByName(name);
        if (oldCourse.isPresent()) {
            return false;
        }
        Course c = new Course();
        c.setName(name);
        c.setAddress(address);
        c.setCamera(camera);
        c.setNumOfStudent(numOfStudent);
        c.setInterval(interval);
        c.setUser(user);
        Set<TimeSlot> timeSlots = new HashSet<>();
        convertTimeToTimeSlot(time, timeSlots);
        c.setTimeSlots(timeSlots);
        courseRepository.save(c);
        return true;
    }

    @Override
    public boolean deleteCourse(String name) {
        Optional<Course> c = courseRepository.findByName(name);
        if (!c.isPresent()) {
            return false;
        }
        courseRepository.delete(c.get());
        return true;
    }

    @Override
    public boolean updateCourse(String oldName, String name, String address, String camera, Integer numOfStudent, Integer interval, List<SpecificTime> time) {
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
        Set<TimeSlot> timeSlots = new HashSet<>();
        convertTimeToTimeSlot(time, timeSlots);
        c.setTimeSlots(timeSlots);
        courseRepository.save(c);
        return true;
    }

    private void convertTimeToTimeSlot(List<SpecificTime> time, Set<TimeSlot> timeSlots) {
        for (SpecificTime t : time) {
            TimeSlot ts = new TimeSlot(); // TODO: 从time_slot表中取对应数据
            ts.setDay(t.getDay());
            ts.setStartTime(t.getStartTime());
            ts.setEndTime(t.getEndTime());
            timeSlots.add(ts);
        }
    }

}
