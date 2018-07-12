package com.example.ktws.domain;

import javax.persistence.*;

@Entity
public class Course { //TODO：关联user和section
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long ud; //TODO: 把外键改为spring-jpa注解方式

    private String name;

    private String address;

    private String camera;

    private Integer num_of_student;

    private Integer interval;

//    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUd() { return ud; }

    public void setUd(Long ud) { this.ud = ud; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public Integer getNum_of_student() {
        return num_of_student;
    }

    public void setNum_of_student(Integer num_of_student) { this.num_of_student = num_of_student; }

    public Integer getInterval() { return interval; }

    public void setInterval(Integer intervals) { this.interval = intervals; }
}
