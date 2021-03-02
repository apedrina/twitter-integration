package com.alissonpedrina.producer.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

import java.util.Date;

@Slf4j
@Component
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TwitterQuery twitterQuery;

    @Scheduled(fixedRate = 20000)
    public void produce() {
        if (TwitterConnect.authenticated) {
            log.info("kafka schedule service: " + new Date());
            try {
                var msg = twitterQuery.queryOnTwitter(TwitterConnect.twitter);
                this.kafkaTemplate.sendDefault(msg);

            } catch (TwitterException ex) {
                log.error("was impossible query on twitter: " + ex.getMessage());
                ex.printStackTrace();
            }

        }

    }

}