package com.splash.splash_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddScoreRequestDto {
    private String userName;
    private String date;
    private int score;
}
