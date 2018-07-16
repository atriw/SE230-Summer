package com.example.ktws.service;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import com.example.ktws.util.SpecificTime;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Iterable<Course> getAllCourses();

    Iterable<Course> getCoursesByUser(User user);

    Optional<Course> findById(Long id);

    Course addNewCourse(String name, String address, String camera, Integer numOfStudent, Integer interval, List<SpecificTime> time, User user);

    boolean deleteCourse(String name);

    boolean updateCourse(String oldName, String name, String address, String camera, Integer numOfStudent, Integer interval, List<SpecificTime> time);
}
