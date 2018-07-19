package com.example.ktws.service.Impl;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.dto.PhotoDTO;
import com.example.ktws.repository.PhotoRepository;
import com.example.ktws.service.PhotoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<PhotoDTO> getPhotoById(Long pid) {
        try {
            GridFSFile found = gridFsTemplate.findOne(new Query(GridFsCriteria.whereMetaData("photoId").is(String.valueOf(pid))));
            if (found == null) {
                logger.info("GetPhotoById: Photo [id={}] not found", pid);
                return Optional.empty();
            }
            GridFsResource resource = download(found.getObjectId().toString());
            InputStream inputStream = resource.getInputStream();
            PhotoDTO photoDTO = new PhotoDTO();
            photoDTO.setPhotoId(Long.parseLong(found.getMetadata().getString("photoId")));
            photoDTO.setData(inputStream);
            photoDTO.setContentType("image/x-png");
            logger.info("GetPhotoById: Got photo [id={}]", pid);
            return Optional.of(photoDTO);
        } catch (Exception e) {
            logger.error("Failed to get photo");
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
            logger.info("PutPhotoByUrl: Stored photo [pid={}] from url {}", pid, url);
            return true;
        } catch (IOException e) {
            logger.error("Failed to store photo");
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
        logger.info("AddNewPhoto: Added photo {} from {}", photo, url);
        return photo;
    }

    public GridFsResource download(String fileId) {
        GridFSFile file = gridFsTemplate.findOne(Query.query(GridFsCriteria.where("_id").is(fileId)));
        if (file == null) {
            return null;
        }
        return new GridFsResource(file, getGridFs().openDownloadStream(file.getObjectId()));
    }

    private GridFSBucket getGridFs() {

        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }
}
