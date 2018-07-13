package com.example.ktws.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
    private Set<Stat> Stats;

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
        return Stats;
    }

    public void setStats(Set<Stat> stats) {
        Stats = stats;
    }
}
