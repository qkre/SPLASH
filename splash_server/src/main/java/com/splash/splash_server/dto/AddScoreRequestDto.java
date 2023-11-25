package com.splash.splash_server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddScoreRequestDto {
    private String userName;
    private int week;
    private int firstScore;
    private int secondScore;
    private int thirdScore;
    private int dayTotalScore;

    public AddScoreRequestDto(String userName, int week, int firstScore, int secondScore, int thirdScore, int dayTotalScore) {
        this.userName = userName;
        this.week = week;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.thirdScore = thirdScore;
        this.dayTotalScore = dayTotalScore;
    }
}
