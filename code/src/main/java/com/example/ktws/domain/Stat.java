package com.example.ktws.domain;

import com.example.ktws.util.TypeOfFace;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "statistic")
public class Stat {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "num_of_face")
    private Integer numOfFace;
    private TypeOfFace type;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    public Stat(){}

    public Stat(Integer numOfFace, TypeOfFace type, Photo photo){
        this.numOfFace = numOfFace;
        this.type = type;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Stat) {
            Stat stat = (Stat) obj;
            return Objects.equals(id, stat.getId()) &&
                    numOfFace.equals(stat.getNumOfFace())&&
                    type.equals(stat.getType())&&
                    photo.equals(stat.getPhoto());
        }
        return false;
    }

    @Override
    public String toString() {
        return "[id=" +
                this.getId() +
                ", numOfFace=" +
                this.getNumOfFace() +
                ", type=" +
                this.getType().toString() +
                ", photoId=" +
                this.getPhoto().getId() +
                "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOfFace() {
        return numOfFace;
    }

    public void setNumOfFace(Integer numOfFace) {
        this.numOfFace = numOfFace;
    }

    public TypeOfFace getType() {
        return type;
    }

    public void setType(TypeOfFace type) {
        this.type = type;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
