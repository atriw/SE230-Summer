package com.example.ktws.service;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.User;

import java.sql.Timestamp;
import java.util.Optional;

public interface SectionService {
    Section addNewSection(Timestamp dateTime, Course course);

    Iterable<Section> getSectionsByCourse(Course course);

    Iterable<Section> getSectionsByUser(User user);

    Optional<Section> findById(Long id);
}
