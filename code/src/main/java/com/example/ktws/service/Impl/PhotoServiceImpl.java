package com.example.ktws.service.Impl;

import com.example.ktws.domain.Photo;
import com.example.ktws.repository.PhotoRepository;
import com.example.ktws.service.PhotoService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class PhotoServiceImpl implements PhotoService {
    
    @Override
    public InputStream getPhotoById(Integer pid) throws IOException {
        com.mongodb.client.MongoClient mongoClient = MongoClients.create();
        MongoDatabase myDatabase = mongoClient.getDatabase("gridfs");
        GridFSBucket gridFSBucket = GridFSBuckets.create(myDatabase,"ktws");
//        FileOutputStream streamToDownloadTo = new FileOutputStream("123.jpg");
//        gridFSBucket.downloadToStream(fileId, streamToDownloadTo);
//        streamToDownloadTo.close();
//        System.out.println(streamToDownloadTo.toString());

//        //读取本地图片输入流
//        FileInputStream inputStream = new FileInputStream("D:/123.jpg");
//        int i = inputStream.available();
//        //byte数组用于存放图片字节数据
//        byte[] buff = new byte[i];
//        inputStream.read(buff);
//        //记得关闭输入流
//        inputStream.close();
//        //设置发送到客户端的响应内容类型
        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(pid.toString());
        return downloadStream;
    }

    @Override
    public void putPhotoByUrl(String url, Integer pid) {
        com.mongodb.client.MongoClient mongoClient = MongoClients.create();
        MongoDatabase myDatabase = mongoClient.getDatabase("mydb");
        GridFSBucket gridFSBucket = GridFSBuckets.create(myDatabase,"ktws");
        try {
            InputStream streamToUploadFrom = new FileInputStream(new File(url));
            ObjectId fileId = gridFSBucket.uploadFromStream("mongodb-tutorial", streamToUploadFrom);
            gridFSBucket.rename(fileId, pid.toString());
        } catch (FileNotFoundException e){
            System.out.println("ERROR:(" + url + ") Not Found");
        }
    }

    @Override
    public Iterable<Photo> getPhotoByCourseId(Long id) {
        return PhotoRepository.
    }
}
