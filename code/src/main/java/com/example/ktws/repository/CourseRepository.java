package com.example.ktws.repository;

import com.example.ktws.domain.Course;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CourseRepository extends PagingAndSortingRepository<Course,Long> {
    Iterable<Course> findByUser_Id(Long user_id);

    Optional<Course> findByName(String name);
}
