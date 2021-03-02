package com.alissonpedrina.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.alissonpedrina")
@SpringBootApplication(scanBasePackages = {"com.alissonpedrina.consumer"})
@EntityScan(basePackages = {"com.alissonpedrina.consumer.domain"})
@EnableJpaRepositories(basePackages = {"com.alissonpedrina.consumer.repo"})
public class TwitterConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterConsumerApplication.class, args);
    }

}
