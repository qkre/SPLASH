package com.splash.splash_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequestDto {
    private String name;
    private String gender;

    public AddUserRequestDto(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }
}
