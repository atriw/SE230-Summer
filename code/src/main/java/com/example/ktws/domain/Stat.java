package com.example.ktws.domain;

import javax.persistence.*;

@Entity
@Table(name = "statistic")
public class Stat { //TODO: 关联photo，设置枚举类
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Integer num_of_face;
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNum_of_face() {
        return num_of_face;
    }

    public void setNum_of_face(Integer num_of_face) {
        this.num_of_face = num_of_face;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
