package com.splash.splash_server.repository;

import com.splash.splash_server.domain.score.Score;
import com.splash.splash_server.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<List<Score>> findByUserOrderByWeek(User user);
    Optional<Score> findByUserAndWeek(User user, int week);
    Optional<List<Score>> findBySemesterAndWeekOrderByDayTotalScoreDesc(String semester, int week);

    Optional<List<Score>> findByWeekOrderByDayTotalScoreDesc(int week);

    Optional<List<Score>> findByWeekOrderByDayAverageDesc(int week);
    @Query("SELECT distinct semester, week FROM Score ORDER BY semester")
    List<String> findDistinctDate();
}
