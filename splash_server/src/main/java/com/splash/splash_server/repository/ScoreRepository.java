package com.splash.splash_server.repository;

import com.splash.splash_server.domain.score.Score;
import com.splash.splash_server.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<List<Score>> findByUser(User user);
    Optional<Score> findByUserAndDate(User user, String date);
    Optional<Score> findTopByOrderByCreatedAtDesc();
}
