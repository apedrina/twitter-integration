package com.alissonpedrina.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TweetScheduleService {

    public static boolean justDone;

    @Autowired
    private PrintService printService;

    @Scheduled(fixedRate = 30000)
    public void showTweets() {
        if (!justDone) {
            printService.print();

        } else {
            justDone = false;

        }
    }

}
