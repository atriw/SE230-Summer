package com.example.ktws.vo;

import com.example.ktws.domain.Stat;

import java.util.HashSet;

public class StatInfo {
    private Long photoId;

    private Long timestamp;

    private Iterable<Stat> stats = new HashSet<>();

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setStats(Iterable<Stat> stats) {
        this.stats = stats;
    }

    public Iterable<Stat> getStats() {
        return stats;
    }
}
