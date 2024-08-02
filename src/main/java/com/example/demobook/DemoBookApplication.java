package com.example.demobook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;

@SpringBootApplication
@EnableEnversRepositories
public class DemoBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoBookApplication.class, args);
    }

}
