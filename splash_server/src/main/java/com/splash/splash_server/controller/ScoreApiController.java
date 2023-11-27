package com.splash.splash_server.controller;

import com.splash.splash_server.domain.score.Score;
import com.splash.splash_server.domain.score.TotalScore;
import com.splash.splash_server.dto.AddScoreRequestDto;
import com.splash.splash_server.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/score")
@RestController
public class ScoreApiController {

    private final ScoreService scoreService;

    @GetMapping("/week/{semester}/{N}")
    public ResponseEntity<List<Score>> getWeekScore(@PathVariable String semester, @PathVariable int N) {

        return ResponseEntity.ok(scoreService.getWeekScore(semester, N));
    }

    @GetMapping("/dates")
    public ResponseEntity<List<Map<String, Object>>> getDates() {
        return ResponseEntity.ok(scoreService.getDateData());
    }


    @GetMapping("/{week}/{userName}")
    public ResponseEntity<Integer> getUserWeekRank(@PathVariable int week, @PathVariable String userName) {
        return ResponseEntity.ok(scoreService.getUserWeekRank(userName, week));
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
    public ResponseEntity<List<List<Score>>> getScores(@RequestBody String name) {
        return ResponseEntity.ok(scoreService.getUserScore(name));
    }

    @GetMapping("/{semester}/all")
    public ResponseEntity<List<TotalScore>> getSemesterScore(@PathVariable String semester){
        return ResponseEntity.ok(scoreService.getSemesterScore(semester));
    }
}
