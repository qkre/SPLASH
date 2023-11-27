package com.splash.splash_server.service;

import com.splash.splash_server.domain.excel.Excel;
import com.splash.splash_server.domain.user.User;
import com.splash.splash_server.dto.AddUserRequestDto;
import com.splash.splash_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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


    public void saveExcelData(List<Excel> datas){
        for(Excel data: datas){
            String name = data.getName();
            String gender = data.getGender();

            AddUserRequestDto requestDto = new AddUserRequestDto(
                    name, gender
            );
            save(requestDto);
        }
    }

    public List<User> getUsers(String name){
        return userRepository.findByNameContains(name).orElseThrow();
    }
}
