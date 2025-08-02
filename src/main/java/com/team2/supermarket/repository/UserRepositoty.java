package com.team2.supermarket.repository;

import com.team2.supermarket.entity.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepositoty extends MongoRepository<CustomUser,String> {
    Optional<CustomUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
