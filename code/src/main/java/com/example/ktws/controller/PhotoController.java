package com.example.ktws.controller;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import com.example.ktws.domain.User;
import com.example.ktws.service.PhotoService;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {
    @Autowired
    PhotoService PhotoService;

    @GetMapping("/byPid")
    public void getPhotoById(@RequestBody Map map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)throws ServletException, IOException{
        // if somebody knows the format of params , he may directly access photo by url and fake params, other services don't check neither.
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return ;
        }
        Long pid = (Long) map.get("pid");
        OutputStream out = httpServletResponse.getOutputStream();
        PhotoService.getPhotoById(pid,out);
        out.close();
    }

    @GetMapping("/ByCourseId")
    public Iterable<Photo> getByCourseId(@RequestBody Map map,HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        Long id = (Long)map.get("courseId");
        if (id == null){
            return null;
        }
        return PhotoService.getPhotoByCourseId(id);
    }


}
