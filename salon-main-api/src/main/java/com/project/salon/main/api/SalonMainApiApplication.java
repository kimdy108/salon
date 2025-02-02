package com.project.salon.main.api;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SalonMainApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalonMainApiApplication.class, args);
    }

}
