package com.splash.splash_server.service;

import com.splash.splash_server.domain.excel.Excel;
import com.splash.splash_server.domain.score.Score;
import com.splash.splash_server.domain.user.User;
import com.splash.splash_server.dto.AddScoreRequestDto;
import com.splash.splash_server.repository.ScoreRepository;
import com.splash.splash_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScoreService {

    final private ScoreRepository scoreRepository;

    final private UserRepository userRepository;

    public Long save(AddScoreRequestDto requestDto) {
        if (userRepository.findByName(requestDto.getUserName()).isPresent()) {
            User user = userRepository.findByName(requestDto.getUserName()).get();

            return scoreRepository.save(Score.builder().date(requestDto.getDate()).score(requestDto.getScore()).user(user).build()).getScoreKey();

        } else {
            userRepository.save(
                    User.builder()
                            .name(requestDto.getUserName())
                            .gender(0)
                            .build()
            );
            User user = userRepository.findByName(requestDto.getUserName()).get();

            return scoreRepository.save(
                    Score.builder()
                            .date(requestDto.getDate())
                            .score(requestDto.getScore())
                            .user(user)
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

    public String saveWeekData(List<Excel> datas) {

        for (Excel data : datas) {
            String name = data.getName();
            List<Integer> scores = data.getScores();

            for (int i = 1; i <= 3; i++) {
                AddScoreRequestDto requestDto = new AddScoreRequestDto();
                requestDto.setUserName(name);
                requestDto.setDate(" 주차");
                requestDto.setScore(scores.get(i - 1));
                save(requestDto);
            }
        }

        return "Saved";
    }
}
