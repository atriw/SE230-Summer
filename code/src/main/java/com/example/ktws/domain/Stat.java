package com.example.ktws.domain;

import com.example.ktws.util.TypeOfFace;

import javax.persistence.*;

@Entity
@Table(name = "statistic")
public class Stat {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "num_of_face")
    private Integer numOfFace;
    private TypeOfFace type;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "photo_id")
    private Photo photo;

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
