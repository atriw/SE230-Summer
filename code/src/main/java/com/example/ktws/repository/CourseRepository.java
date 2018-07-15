package com.example.ktws.repository;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CourseRepository extends PagingAndSortingRepository<Course,Long> {
    Iterable<Course> findByUser(User user);

    Optional<Course> findByName(String name);
}
