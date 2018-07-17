package com.example.ktws.vo;

import com.example.ktws.domain.Stat;

import java.util.HashSet;

public class StatInfo {
    private Long timestamp;

    private Iterable<Stat> stats = new HashSet<>();

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
