package com.example.ktws.repository;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Section;
import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends CrudRepository<Section, Long> {
    Iterable<Section> findByCourse(Course course);
}
