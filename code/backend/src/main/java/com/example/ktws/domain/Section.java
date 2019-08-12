package com.example.ktws.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Timestamp datetime;

    @JsonIgnore
    @OneToMany(mappedBy = "section", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Photo> photos = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    public Section(){}

    public Section(Timestamp timestamp, Course course){
        this.datetime = timestamp;
        this.course = course;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Section) {
            Section section = (Section) obj;
            return Objects.equals(id, section.getId()) &&
                    datetime.equals(section.getDatetime())&&
                    course.equals(section.getCourse());
        }
        return false;
    }

    @Override
    public String toString() {
        return "[id=" +
                this.getId() +
                ", datetime=" +
                this.getDatetime() +
                ", courseId=" +
                this.getCourse().getId() +
                "]";
    }

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

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
