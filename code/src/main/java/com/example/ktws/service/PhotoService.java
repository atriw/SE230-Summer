package com.example.ktws.service;

import com.example.ktws.domain.Photo;

import java.util.Optional;

public interface PhotoService {
    Optional<Photo> findById(Long id);
}
