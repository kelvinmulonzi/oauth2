package com.example.Oath2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.Oath2.Repo")
public class Oath2Application {

    public static void main(String[] args) {
        SpringApplication.run(Oath2Application.class, args);
    }
}
