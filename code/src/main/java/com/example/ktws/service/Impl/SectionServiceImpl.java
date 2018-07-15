package com.example.ktws.service.Impl;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.User;
import com.example.ktws.repository.SectionRepository;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private CourseService courseService;

    @Override
    public Section addNewSection(Long dateTime, Course course) {
        Section s = new Section();
        s.setDatetime(dateTime);
        s.setCourse(course);
        return sectionRepository.save(s);
    }

    @Override
    public Iterable<Section> getSectionsByCourse(Course course) {
        return sectionRepository.findByCourse(course);
    }

    @Override
    public Optional<Section> findById(Long Id) {
        return Optional.empty();
    }
}
