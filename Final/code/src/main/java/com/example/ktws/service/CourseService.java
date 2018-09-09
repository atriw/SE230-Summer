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

    Course addNewCourse(Course c, List<SpecificTime> time);

    boolean deleteCourse(Long id);

    boolean updateCourse(String oldName, String name, String address, String camera, Integer numOfStudent, Integer interval, List<SpecificTime> time);
}
