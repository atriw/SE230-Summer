package com.example.ktws.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface PhotoService {
    InputStream getPhotoById(Integer pid) throws IOException;
    void putPhotoByUrl(String url, Integer pid);
}
