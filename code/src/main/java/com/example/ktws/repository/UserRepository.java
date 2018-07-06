package com.example.ktws.repository;

import com.example.ktws.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    List<User> findByName(String name);
}
