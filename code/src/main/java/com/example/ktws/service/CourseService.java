package com.example.ktws.service;

import com.example.ktws.domain.Course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface CourseService {

    Iterable<Course> getAllCourses();

    Iterable<Course> getCoursesByUd(HttpSession session);

    boolean addNewCourse(Long ud, String name, String address, String camera, Integer num_of_student, Integer interval);

    boolean deleteCourse(String name);

    boolean updateCourse(long ud, String oldName, String name, String address, String camera, Integer num_of_student, Integer interval);
}
