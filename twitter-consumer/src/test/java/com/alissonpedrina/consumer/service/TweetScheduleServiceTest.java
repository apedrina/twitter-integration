package com.alissonpedrina.consumer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TweetScheduleServiceTest {

    @Mock
    private PrintService printService;

    @InjectMocks
    private TweetScheduleService tweetScheduleService;

    @Test
    void should_call_print_service() {
        tweetScheduleService.showTweets();

        verify(printService, times(1)).print();

    }

    @Test
    void should_not_call_print_service() {
        TweetScheduleService.justDone = true;

        tweetScheduleService.showTweets();

        verify(printService, times(0)).print();

    }

}
