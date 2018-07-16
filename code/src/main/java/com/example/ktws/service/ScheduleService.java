package com.example.ktws.service;

import java.util.List;

public interface ScheduleService {
    boolean add(Long courseId, String camera, Integer interval, List<String> cronExpression, Integer duration) throws Exception;

    boolean modify(Long courseId, String camera, Integer interval, List<String> cronExpression, Integer duration) throws Exception;

    boolean delete(Long courseId) throws Exception;
}
