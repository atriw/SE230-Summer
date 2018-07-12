package com.example.ktws.repository;

import com.example.ktws.domain.Course;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CourseRepository extends PagingAndSortingRepository<Course,Long> {
    Course findByName(String name);

    List<Course> findCourseByUd(Long ud);

}
