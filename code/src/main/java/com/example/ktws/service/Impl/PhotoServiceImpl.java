package com.example.ktws.service.Impl;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.repository.PhotoRepository;
import com.example.ktws.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public Optional<Photo> findById(Long id) {
        return photoRepository.findById(id);
    }

    @Override
    public Photo addNewPhoto(Long timestamp, Section section, String url) {
        // TODO: 实现存入MYSQL和MONGODB
        Photo photo = new Photo();
        photo.setTimestamp(timestamp);
        photo.setSection(section);
        return photoRepository.save(photo);
    }
}
