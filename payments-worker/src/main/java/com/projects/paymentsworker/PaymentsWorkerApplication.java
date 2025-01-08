package com.projects.paymentsworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRedisRepositories(basePackages = {"repository"})
@EnableScheduling
@ComponentScan({"repository", "com.projects.paymentsworker.scheduler"})
public class PaymentsWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsWorkerApplication.class, args);
    }

}
