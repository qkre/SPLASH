package com.splash.splash_server.repository;

import com.splash.splash_server.domain.score.Score;
import com.splash.splash_server.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<List<Score>> findByUser(User user);
    Optional<Score> findByUserAndWeek(User user, int week);
    Optional<List<Score>> findByWeek(int week);
    Optional<Score> findTopByOrderByCreatedAtDesc();

    @Query("SELECT DISTINCT s.week FROM Score s ORDER BY s.week ")
    List<Integer> findDistinctDateByOrderByWeekDesc();
}
