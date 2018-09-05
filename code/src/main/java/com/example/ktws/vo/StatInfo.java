package com.example.ktws.vo;

import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Stat;
import com.example.ktws.util.TypeOfFace;

import java.util.HashSet;

public class StatInfo {
    private Long photoId;

    private Long timestamp;

    private Iterable<Stat> stats = new HashSet<>();

    public StatInfo() {}

    public StatInfo(Photo photo) {
        this.photoId = photo.getId();
        this.timestamp = photo.getTimestamp();
        this.stats = photo.getStats();
    }

    public float emotionCount() {
        float count = 0;
        for (Stat stat : this.stats) {
            TypeOfFace type = stat.getType();
            if (type == TypeOfFace.SADNESS) {
                count += 0.3 * stat.getNumOfFace();
            }
            if (type == TypeOfFace.NEUTRAL) {
                count += 0.9 * stat.getNumOfFace();
            }
            if (type == TypeOfFace.DISGUST) {
                count += 0.1 * stat.getNumOfFace();
            }
            if (type == TypeOfFace.ANGER) {
                count += 0.1 * stat.getNumOfFace();
            }
            if (type == TypeOfFace.SURPRISE) {
                count += 0.8 * stat.getNumOfFace();
            }
            if (type == TypeOfFace.FEAR) {
                count += 0.1 * stat.getNumOfFace();
            }
            if (type == TypeOfFace.HAPPINESS) {
                count += 1 * stat.getNumOfFace();
            }
        }
        return count;
    }

    public Integer getNumOfFace() {
        for (Stat stat : this.stats) {
            if (stat.getType() == TypeOfFace.ALL) {
                return stat.getNumOfFace();
            }
        }
        return 0;
    }

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
