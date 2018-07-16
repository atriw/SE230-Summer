package com.example.ktws.repository;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course,Long> {
    Iterable<Course> findByUser(User user);

    Optional<Course> findByName(String name);
}
