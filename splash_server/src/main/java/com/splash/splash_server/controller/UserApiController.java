package com.splash.splash_server.controller;

import com.splash.splash_server.dto.AddUserRequestDto;
import com.splash.splash_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/sign")
    public ResponseEntity<String> signUp(@RequestBody AddUserRequestDto requestDto){
        Long result = userService.save(requestDto);

        if(result == -1l){
            return ResponseEntity.badRequest().body("Failed to sign up.");
        } else{
            return ResponseEntity.ok("Successfully sign up. Your key is " + result);
        }
    }

    @GetMapping("/test/{name}")
    public ResponseEntity<String> test(@PathVariable String name){

        Long key = userService.getUserKey(name);

        return ResponseEntity.ok("Key is " + key);
    }
}
