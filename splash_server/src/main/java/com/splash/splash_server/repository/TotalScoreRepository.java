package com.splash.splash_server.repository;

import com.splash.splash_server.domain.score.TotalScore;
import com.splash.splash_server.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalScoreRepository extends JpaRepository<TotalScore, Long> {
    Optional<TotalScore> findByUser(User user);
}
