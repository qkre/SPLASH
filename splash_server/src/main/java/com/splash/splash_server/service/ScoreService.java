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

            if (scoreRepository.findByUserAndDate(user, requestDto.getDate()).isPresent()) {
                Score existData = scoreRepository.findByUserAndDate(user, requestDto.getDate()).get();

                existData.setFirstScore(requestDto.getFirstScore());
                existData.setSecondScore(requestDto.getSecondScore());
                existData.setThirdScore(requestDto.getThirdScore());
                existData.setDayTotalScore(requestDto.getDayTotalScore());

                return scoreRepository.save(existData).getScoreKey();
            } else {
                return scoreRepository.save(
                        Score.builder()
                                .user(user)
                                .date(requestDto.getDate())
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
                            .date(requestDto.getDate())
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
                String date = data.getDate();
                int firstScore = data.getFirstScore();
                int secondScore = data.getSecondScore();
                int thirdScore = data.getThirdScore();
                int dayTotalScore = data.getDayTotalScore();
                int totalScore = data.getTotalScore();
                int played = data.getPlayed();
                double average = data.getAverage();

                AddScoreRequestDto requestDto = new AddScoreRequestDto(
                        name, date, firstScore, secondScore, thirdScore, dayTotalScore
                );

                save(requestDto);


                saveTotalScore(name, totalScore, played, average);

            }

            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void saveTotalScore(String name, int totalScore, int played, double average) {
        User user = userRepository.findByName(name).orElseThrow();

        if (totalScoreRepository.findByUser(user).isPresent()) {
            TotalScore existData = totalScoreRepository.findByUser(user).get();
            existData.setTotalScore(totalScore);
            existData.setPlayed(played);
            existData.setAverage(average);
            totalScoreRepository.save(existData);
        } else {
            totalScoreRepository.save(
                    TotalScore.builder()
                            .user(user)
                            .totalScore(totalScore)
                            .played(played)
                            .average(average)
                            .build()
            );
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

    public List<Score> getWeekScore(String date){
        List<Score> scores = scoreRepository.findByDate(date).orElseThrow();

        return scores;
    }

    public List<TotalScore> getAllScore() {
        List<TotalScore> scores = totalScoreRepository.findAll();

        return scores;
    }
}
