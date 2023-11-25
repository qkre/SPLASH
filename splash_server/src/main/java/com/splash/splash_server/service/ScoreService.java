package com.splash.splash_server.service;

import com.splash.splash_server.domain.excel.Excel;
import com.splash.splash_server.domain.score.Score;
import com.splash.splash_server.domain.score.TotalScore;
import com.splash.splash_server.domain.user.User;
import com.splash.splash_server.dto.AddScoreRequestDto;
import com.splash.splash_server.repository.ScoreRepository;
import com.splash.splash_server.repository.TotalScoreRepository;
import com.splash.splash_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScoreService {

    final private TotalScoreRepository totalScoreRepository;

    final private ScoreRepository scoreRepository;

    final private UserRepository userRepository;

    public Long save(AddScoreRequestDto requestDto) {
        if (userRepository.findByName(requestDto.getUserName()).isPresent()) {
            User user = userRepository.findByName(requestDto.getUserName()).get();

            if (scoreRepository.findByUserAndWeek(user, requestDto.getWeek()).isPresent()) {
                Score existData = scoreRepository.findByUserAndWeek(user, requestDto.getWeek()).get();

                existData.setFirstScore(requestDto.getFirstScore());
                existData.setSecondScore(requestDto.getSecondScore());
                existData.setThirdScore(requestDto.getThirdScore());
                existData.setDayTotalScore(requestDto.getDayTotalScore());

                return scoreRepository.save(existData).getScoreKey();
            } else {
                return scoreRepository.save(
                        Score.builder()
                                .user(user)
                                .week(requestDto.getWeek())
                                .firstScore(requestDto.getFirstScore())
                                .secondScore(requestDto.getSecondScore())
                                .thirdScore(requestDto.getThirdScore())
                                .dayTotalScore(requestDto.getDayTotalScore())
                                .build()
                ).getScoreKey();
            }

        } else {
            userRepository.save(
                    User.builder()
                            .gender(0)
                            .name(requestDto.getUserName())
                            .build()
            );
            User user = userRepository.findByName(requestDto.getUserName()).get();
            return scoreRepository.save(
                    Score.builder()
                            .user(user)
                            .firstScore(requestDto.getFirstScore())
                            .secondScore(requestDto.getSecondScore())
                            .thirdScore(requestDto.getThirdScore())
                            .dayTotalScore(requestDto.getDayTotalScore())
                            .week(requestDto.getWeek())
                            .build()
            ).getScoreKey();
        }
    }

    public List<Score> getUserScore(String userName) {

        Optional<User> userOptional = userRepository.findByName(userName);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            return scoreRepository.findByUser(user).get();
        } else {
            return new ArrayList<>();
        }
    }

    public boolean saveExcelData(List<Excel> datas) {
        try {
            for (Excel data : datas) {
                String name = data.getName();
                int week = data.getWeek();
                int firstScore = data.getFirstScore();
                int secondScore = data.getSecondScore();
                int thirdScore = data.getThirdScore();
                int dayTotalScore = data.getDayTotalScore();

                AddScoreRequestDto requestDto = new AddScoreRequestDto(
                        name, week, firstScore, secondScore, thirdScore, dayTotalScore
                );

                save(requestDto);

                saveTotalScore(requestDto);

            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveTotalScore(AddScoreRequestDto requestDto) {
        User user = userRepository.findByName(requestDto.getUserName()).orElseThrow();

        if (totalScoreRepository.findByUser(user).isPresent()) {
            TotalScore target = totalScoreRepository.findByUser(user).get();
            int targetTotalScore = target.getTotalScore();
            double targetAverage = target.getAverage();
            int targetPlayed = target.getPlayed();
            int played = 0;

            if (requestDto.getFirstScore() != 0) played++;
            if (requestDto.getSecondScore() != 0) played++;
            if (requestDto.getThirdScore() != 0) played++;

            targetTotalScore += requestDto.getDayTotalScore();
            targetPlayed += played;
            targetAverage = targetTotalScore / targetPlayed;

            target.setTotalScore(targetTotalScore);
            target.setPlayed(targetPlayed);
            target.setAverage(targetAverage);
            totalScoreRepository.save(target);
        } else {
            TotalScore target = new TotalScore();

            int targetTotalScore = 0;
            double targetAverage = 0.0;
            int targetPlayed = 0;
            int played = 0;
            if (requestDto.getFirstScore() != 0) played++;
            if (requestDto.getSecondScore() != 0) played++;
            if (requestDto.getThirdScore() != 0) played++;

            targetTotalScore += requestDto.getDayTotalScore();
            targetPlayed += played;
            targetAverage = targetTotalScore / targetPlayed;

            targetAverage = Math.round(targetAverage * 100.0) / 100.0;

            target.setUser(user);
            target.setTotalScore(targetTotalScore);
            target.setAverage(targetAverage);
            target.setPlayed(targetPlayed);

            totalScoreRepository.save(target);
        }
    }

    public int getTotalScore(String name) {
        User user = userRepository.findByName(name).orElseThrow();

        return totalScoreRepository.findByUser(user).get().getTotalScore();
    }

    public Double getAverageScore(String name) {
        User user = userRepository.findByName(name).orElseThrow();

        return totalScoreRepository.findByUser(user).get().getAverage();
    }

    public List<Score> getWeekScore(int week) {
        List<Score> scores = scoreRepository.findByWeek(week).orElseThrow();

        return scores;
    }

    public List<TotalScore> getAllScore() {
        List<TotalScore> scores = totalScoreRepository.findAllByOrderByAverageDesc();

        return scores;
    }

    public List<Integer> getWeeksData(){
        List<Integer> dates = scoreRepository.findDistinctDateByOrderByWeekDesc();

        return dates;
    }
}
