package com.example.ktws.service;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import com.example.ktws.util.SpecificTime;

import java.util.List;

public interface CourseService {

    Iterable<Course> getAllCourses();

    Iterable<Course> getCoursesByUser(User user);

    boolean addNewCourse(String name, String address, String camera, Integer numOfStudent, Integer interval, List<SpecificTime> time, User user);

    boolean deleteCourse(String name);

    boolean updateCourse(String oldName, String name, String address, String camera, Integer numOfStudent, Integer interval, List<SpecificTime> time);
}
