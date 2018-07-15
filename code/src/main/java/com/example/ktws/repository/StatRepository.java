package com.example.ktws.repository;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Stat;
import org.springframework.data.repository.CrudRepository;

public interface StatRepository extends CrudRepository<Stat, Long> {
    Iterable<Stat> findByPhoto(Photo photo);
}
