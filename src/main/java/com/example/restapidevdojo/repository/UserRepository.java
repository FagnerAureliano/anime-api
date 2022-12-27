package com.example.restapidevdojo.repository;

import com.example.restapidevdojo.domain.Anime;
import com.example.restapidevdojo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
