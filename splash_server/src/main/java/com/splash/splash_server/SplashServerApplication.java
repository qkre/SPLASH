package com.splash.splash_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SplashServerApplication {

    public static void main(String[] args) {


        try{SpringApplication.run(SplashServerApplication.class, args);} catch (Exception e){
            e.printStackTrace();
        };
    }

}
