package com.splash.splash_server.controller;

import com.splash.splash_server.domain.score.Score;
import com.splash.splash_server.domain.score.TotalScore;
import com.splash.splash_server.dto.AddScoreRequestDto;
import com.splash.splash_server.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/score")
@RestController
public class ScoreApiController {

    private final ScoreService scoreService;

    @GetMapping("/week/{N}")
    public ResponseEntity<List<Score>> getWeekScore(@PathVariable String N) {

        return ResponseEntity.ok(scoreService.getWeekScore(N));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TotalScore>> getAllScore(){
        return ResponseEntity.ok(scoreService.getAllScore());
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AddScoreRequestDto requestDto) {
        Long result = scoreService.save(requestDto);

        if (result == -1L) {
            return ResponseEntity.badRequest().body("Failed to save score. Not exist user.");
        } else {
            return ResponseEntity.ok("Successfully saved. Score key is " + result);
        }
    }

    @PostMapping("/last")
    public ResponseEntity<List<Score>> getScores(@RequestBody String name) {
        return ResponseEntity.ok(scoreService.getUserScore(name));
    }

    @PostMapping("/total")
    public ResponseEntity<Integer> getTotalScore(@RequestBody String name) {
        return ResponseEntity.ok(scoreService.getTotalScore(name));
    }

    @PostMapping("/average")
    public ResponseEntity<Double> getAverageScore(@RequestBody String name) {
        return ResponseEntity.ok(scoreService.getAverageScore(name));
    }
}
