package com.example.ktws.service;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.Stat;
import com.mongodb.client.gridfs.GridFSDownloadStream;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public interface PhotoService {
    void getPhotoById(Long pid, OutputStream out) throws IOException;
    void putPhotoByUrl(String url, Long pid);
    Iterable<Photo> getPhotosBySection(Section section);
    Optional<Photo> findById(Long id);
    Photo addNewPhoto(Long timestamp, Section section, String url);
}
