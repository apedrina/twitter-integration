package com.alissonpedrina.producer.service;

import com.alissonpedrina.producer.producer.service.KafkaService;
import com.alissonpedrina.producer.producer.service.TwitterConnect;
import com.alissonpedrina.producer.producer.service.TwitterQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import twitter4j.TwitterException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaServiceTest {

    @Mock
    private TwitterQuery twitterQuery;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaService kafkaService;

    @Test
    void should_send_message_to_kafka() throws TwitterException {
        TwitterConnect.authenticated = true;
        var msg = "@John 2021";
        when(twitterQuery.queryOnTwitter(Mockito.any())).thenReturn(msg);
        when(kafkaTemplate.sendDefault(msg)).thenReturn(Mockito.any());

        kafkaService.produce();

        verify(kafkaTemplate, Mockito.times(1)).sendDefault(msg);

    }

    @Test
    void should_not_execute_the_search() throws TwitterException {
        TwitterConnect.authenticated = false;

        kafkaService.produce();

        verify(kafkaTemplate, Mockito.times(0)).sendDefault(Mockito.any());

    }

}
