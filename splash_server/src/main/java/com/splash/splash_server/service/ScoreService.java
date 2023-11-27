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
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ScoreService {

    final private TotalScoreRepository totalScoreRepository;

    final private ScoreRepository scoreRepository;

    final private UserRepository userRepository;


    public Long save(AddScoreRequestDto requestDto) {
        int played = 0;
        if (requestDto.getFirstScore() != 0) played++;
        if (requestDto.getSecondScore() != 0) played++;
        if (requestDto.getThirdScore() != 0) played++;

        User user = userRepository.findByName(requestDto.getUserName()).orElseThrow();

        if (scoreRepository.findByUserAndWeek(user, requestDto.getWeek()).isPresent()) {
            Score existData = scoreRepository.findByUserAndWeek(user, requestDto.getWeek()).get();

            existData.setFirstScore(requestDto.getFirstScore());
            existData.setSecondScore(requestDto.getSecondScore());
            existData.setThirdScore(requestDto.getThirdScore());
            existData.setDayTotalScore(requestDto.getDayTotalScore());
            existData.setSemester(requestDto.getSemester());

            existData.setDayAverage((double) requestDto.getDayTotalScore() / played);

            return scoreRepository.save(existData).getScoreKey();
        } else {
            Score score = Score.builder()
                    .user(user)
                    .week(requestDto.getWeek())
                    .firstScore(requestDto.getFirstScore())
                    .secondScore(requestDto.getSecondScore())
                    .thirdScore(requestDto.getThirdScore())
                    .dayTotalScore(requestDto.getDayTotalScore())
                    .semester(requestDto.getSemester())
                    .build();

            score.setDayAverage((double) requestDto.getDayTotalScore() / played);

            return scoreRepository.save(score).getScoreKey();

        }

    }

    public void saveTotalScore(AddScoreRequestDto requestDto) {
        User user = userRepository.findByName(requestDto.getUserName()).orElseThrow();
        int played = 0;

        if (requestDto.getFirstScore() != 0) played++;
        if (requestDto.getSecondScore() != 0) played++;
        if (requestDto.getThirdScore() != 0) played++;


        if (totalScoreRepository.findByUserAndSemester(user, requestDto.getSemester()).isPresent()) {
            TotalScore existData = totalScoreRepository.findByUserAndSemester(user, requestDto.getSemester()).get();

            existData.setTotalScore(existData.getTotalScore() + requestDto.getDayTotalScore());
            existData.setPlayed(existData.getPlayed() + played);
            existData.setAverage((double) existData.getTotalScore() / existData.getPlayed());

            totalScoreRepository.save(existData);
        } else {
            TotalScore newData = TotalScore.builder().user(user).build();
            newData.setTotalScore(requestDto.getDayTotalScore());
            newData.setPlayed(played);
            newData.setAverage((double) requestDto.getDayTotalScore() / played);
            newData.setSemester(requestDto.getSemester());

            totalScoreRepository.save(newData);
        }
    }

    public int getUserWeekRank(String userName, int week) {
        List<Score> scores = scoreRepository.findByWeekOrderByDayTotalScoreDesc(week).orElseThrow();
        int rank = 0;

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).getUser().getName().contains(userName)) {
                rank = i + 1;
                break;
            }
        }

        return rank;
    }

    public List<List<Score>> getUserScore(String userName) {

        List<User> users = userRepository.findByNameContains(userName).orElseThrow();

        List<List<Score>> scoreList = new ArrayList<>();

        for (User user : users) {
            List<Score> scores = scoreRepository.findByUserOrderByWeek(user).orElseThrow();
            scoreList.add(scores);
        }

        return scoreList;
    }

    public boolean saveExcelData(List<Excel> datas) {
        try {
            int week = -1;
            for (Excel data : datas) {

                String name = data.getName();
                String gender = data.getGender();
                week = data.getWeek();
                int firstScore = data.getFirstScore();
                int secondScore = data.getSecondScore();
                int thirdScore = data.getThirdScore();
                int dayTotalScore = data.getDayTotalScore();
                String semester = data.getSemester();


                AddScoreRequestDto requestDto = new AddScoreRequestDto(
                        name = name,
                        week = week,
                        firstScore = firstScore,
                        secondScore = secondScore,
                        thirdScore = thirdScore,
                        dayTotalScore = dayTotalScore,
                        semester = semester);

                save(requestDto);

                saveTotalScore(requestDto);

            }

            saveWeekRank(week);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveWeekRank(int week) {
        List<Score> scores = scoreRepository.findByWeekOrderByDayAverageDesc(week).orElseThrow();

        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            score.setRanks(i + 1);
            scoreRepository.save(score);
        }
    }

    public List<TotalScore> getSemesterScore(String semester){
        List<TotalScore> totalScores = totalScoreRepository.findBySemesterOrderByAverageDesc(semester).orElseThrow();

        return totalScores;
    }

    public List<Score> getWeekScore(String semester, int week) {
        List<Score> scores = scoreRepository.findBySemesterAndWeekOrderByDayTotalScoreDesc(semester, week).orElseThrow();

        return scores;
    }


    public List<Map<String, Object>> getDateData() {
        List<String> dates = scoreRepository.findDistinctDate();

        List<Map<String, Object>> jsonDateData = new ArrayList<>();

        for (String date : dates) {
            Map<String, Object> jsonDate = new HashMap<>();
            jsonDate.put("semester", date.split(",")[0]);
            jsonDate.put("week", date.split(",")[1]);

            jsonDateData.add(jsonDate);
        }

        return jsonDateData;
    }
}
