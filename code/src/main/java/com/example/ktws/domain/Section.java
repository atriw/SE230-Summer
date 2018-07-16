package com.example.ktws.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long datetime;

    @OneToMany(mappedBy = "section", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Photo> photos = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    public void addPhoto(Photo photo) {
        if (photos.contains(photo)) {
            return;
        }
        photos.add(photo);
        photo.setSection(this);
    }

    public void removePhoto(Photo photo) {
        if (!photos.contains(photo)) {
            return;
        }
        photos.remove(photo);
        photo.setSection(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}