package com.example.ktws.service;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.User;

import java.util.Optional;

public interface SectionService {
    Section addNewSection(Long dateTime, Course course);

    Iterable<Section> getSectionsByCourse(Course course);

    Optional<Section> findById(Long Id);
}
