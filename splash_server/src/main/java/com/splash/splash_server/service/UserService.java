package com.splash.splash_server.service;

import com.splash.splash_server.domain.user.User;
import com.splash.splash_server.dto.AddUserRequestDto;
import com.splash.splash_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(AddUserRequestDto requestDto) {
        if (userRepository.findByName(requestDto.getName()).isPresent()) {
            return -1L;
        }

        if (requestDto.getName().isEmpty()) {
            return -1L;
        }

        return userRepository.save(
                User.builder().name(requestDto.getName()).gender(requestDto.getGender()).build()
        ).getUserKey();
    }

    public Long getUserKey(String name){
        if (userRepository.findByName(name).isPresent()){
            return userRepository.findByName(name).get().getUserKey();
        } else{
            return -1L;
        }
    }
}
