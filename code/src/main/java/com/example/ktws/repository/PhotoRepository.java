package com.example.ktws.repository;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
    Iterable<Photo> findBySection_Id(Long section_id);
}
