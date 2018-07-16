package com.example.ktws.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PhotoService {
    void getPhotoById(Integer pid, HttpServletResponse httpServletResponse) throws IOException;
    void putPhotoByUrl(String url, Integer pid);
}
