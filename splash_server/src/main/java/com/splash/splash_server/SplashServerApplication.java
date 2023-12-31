package com.splash.splash_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SplashServerApplication {

    public static void main(String[] args) {


        try{SpringApplication.run(SplashServerApplication.class, args);} catch (Exception e){
            e.printStackTrace();
        };
    }

}
