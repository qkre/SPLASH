package com.splash.splash_server.controller;

import com.splash.splash_server.domain.score.Score;
import com.splash.splash_server.dto.AddScoreRequestDto;
import com.splash.splash_server.service.ScoreService;
import com.splash.splash_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/score")
@Controller
public class ScoreApiController {

    private final ScoreService scoreService;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AddScoreRequestDto requestDto){
        Long result = scoreService.save(requestDto);

        if (result == -1L){
            return ResponseEntity.badRequest().body("Failed to save score. Not exist user.");
        } else {
            return ResponseEntity.ok("Successfully saved. Score key is "+result);
        }
    }

    @GetMapping("/{name}/getscores")
    public ResponseEntity<List<Score>> getScores(@PathVariable String name){
        return ResponseEntity.ok(scoreService.getUserScore(name));
    }
}
