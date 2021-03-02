package com.alissonpedrina.producer;

import com.alissonpedrina.producer.producer.service.TwitterConnect;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.alissonpedrina.producer")
@SpringBootApplication(scanBasePackages = {"com.alissonpedrina.producer"})
public class TwitterApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class, args);

    }

    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        TwitterConnect.connect();

    }

}
