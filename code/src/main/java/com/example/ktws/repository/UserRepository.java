package com.example.ktws.repository;

import com.example.ktws.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    Optional<User> findByName(String name);
}
