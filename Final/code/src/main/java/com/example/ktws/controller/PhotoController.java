package com.example.ktws.controller;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.User;
import com.example.ktws.dto.PhotoDTO;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.SectionService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {
    @Autowired
    private PhotoService PhotoService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SectionService sectionService;

    @PostMapping("/store")
    public Photo store(@RequestBody Map map) {
        Long sectionId = Long.parseLong((String) map.get("sectionId"));
        Optional<Section> existing = sectionService.findById(sectionId);
        if (!existing.isPresent()) {
            return null;
        }
        Section section = existing.get();
        String url = (String) map.get("url");
        return PhotoService.addNewPhoto(System.currentTimeMillis(), section, url);
    }

    @GetMapping("/byPhotoId")
    public HttpEntity<byte[]> getPhotoById(@RequestParam(name = "photoId") Long photoId, HttpServletRequest httpServletRequest) throws IOException{
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Optional<PhotoDTO> existing = PhotoService.getPhotoById(photoId);
        if (!existing.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PhotoDTO photoDTO = existing.get();

        IOUtils.copy(photoDTO.getData(), out);
        String contentType = photoDTO.getContentType();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        return new HttpEntity<>(out.toByteArray(), headers);
    }

    @GetMapping("/byCourseId")
    public Iterable<Photo> getByCourseId(@RequestParam(name = "courseId") Long courseId, HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        return courseService.findById(courseId)
                .<Iterable<Photo>>map(course -> course.getSections().stream()
                        .flatMap(section -> section.getPhotos().stream())
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
