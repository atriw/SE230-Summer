package com.example.ktws.service;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;

import java.util.Optional;

public interface PhotoService {
    Optional<Photo> findById(Long id);

    Photo addNewPhoto(Long timestamp, Section section, String url);
}
