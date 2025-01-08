package com.projects.paymentsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackages = {"repository"})
public class PaymentsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsAppApplication.class, args);
    }

}
