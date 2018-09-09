package com.example.ktws.repository;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
    Iterable<Photo> findBySection(Section section);
}
