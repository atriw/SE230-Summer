package com.example.ktws.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long timestamp;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
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
