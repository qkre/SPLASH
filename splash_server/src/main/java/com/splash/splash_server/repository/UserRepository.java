package com.splash.splash_server.repository;

import com.splash.splash_server.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<List<User>> findByNameContains(String name);
}
