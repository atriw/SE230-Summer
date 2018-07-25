package com.example.ktws.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Photo {
    // TODO: 照片删除时不会删除mongodb里的照片
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long timestamp;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "section_id")
    private Section section;

    @JsonIgnore
    @OneToMany(mappedBy = "photo", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Stat> stats = new HashSet<>();

    public void addStat(Stat stat) {
        if (stats.contains(stat)) {
            return;
        }
        stats.add(stat);
        stat.setPhoto(this);
    }

    public void removeStat(Stat stat) {
        if (!stats.contains(stat)) {
            return;
        }
        stats.remove(stat);
        stat.setPhoto(null);
    }

    public Photo(){}

    public Photo(Long timestamp, Section section){
        this.timestamp = timestamp;
        this.section = section;
    }

    @Override
    public String toString() {
        return "[id=" +
                this.getId() +
                ", timestamp=" +
                this.getTimestamp() +
                ", sectionId=" +
                this.getSection().getId() +
                "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Photo) {
            Photo photo = (Photo) obj;
            return Objects.equals(id, photo.getId()) &&
                    timestamp.equals(photo.getTimestamp())&&
                    section.equals(photo.getSection());
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Set<Stat> getStats() {
        return stats;
    }

    public void setStats(Set<Stat> stats) {
        this.stats = stats;
    }
}
