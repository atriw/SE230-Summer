package com.example.ktws.service.Impl;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.Stat;
import com.example.ktws.dto.PhotoDTO;
import com.example.ktws.repository.PhotoRepository;
import com.example.ktws.service.PhotoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Override
    public Optional<PhotoDTO> getPhotoById(Long pid) {
        try {
            GridFSFile found = gridFsTemplate.findOne(new Query(GridFsCriteria.whereMetaData("photoId").is(String.valueOf(pid))));
            GridFsResource resource = download(found.getObjectId().toString());
            InputStream inputStream = resource.getInputStream();
            PhotoDTO photoDTO = new PhotoDTO();
            photoDTO.setPhotoId(Long.parseLong(found.getMetadata().getString("photoId")));
            photoDTO.setData(inputStream);
            photoDTO.setContentType("image/x-png");
            return Optional.of(photoDTO);
        } catch (Exception e) {
            System.out.println("ERROR: fail to get photo");
            return Optional.empty();
        }
    }

    @Override
    public boolean putPhotoByUrl(String url, Long pid) {
        try {
            DBObject metadata = new BasicDBObject();
            ((BasicDBObject) metadata).put("photoId", String.valueOf(pid));
            File file = new File(url);
            InputStream inputStream = new FileInputStream(file);
            gridFsTemplate.store(inputStream,file.getName(), metadata);
            return true;
        } catch (IOException e) {
            System.out.println("ERROR: fail to store photo");
            return false;
        }
    }

    @Override
    public Iterable<Photo> getPhotosBySection(Section section) {
        return photoRepository.findBySection(section);
    }

    @Override
    public Optional<Photo> findById(Long id) {
        return photoRepository.findById(id);
    }

    @Override
    public Photo addNewPhoto(Long timestamp, Section section, String url) {
        Photo photo = new Photo();
        photo.setTimestamp(timestamp);
        photo.setSection(section);
        photoRepository.save(photo);
        putPhotoByUrl(url, photo.getId());
        return photo;
    }

    public GridFsResource download(String fileId) {
        GridFSFile file = gridFsTemplate.findOne(Query.query(GridFsCriteria.where("_id").is(fileId)));

        return new GridFsResource(file, getGridFs().openDownloadStream(file.getObjectId()));
    }

    private GridFSBucket getGridFs() {

        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }
}
