package com.alissonpedrina.producer.service;

import com.alissonpedrina.producer.model.Author;
import com.alissonpedrina.producer.model.TweetMessage;
import com.alissonpedrina.producer.producer.service.TwitterQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import twitter4j.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TwitterQueryTest {

    private ObjectMapper mapper;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Twitter twitter;

    @InjectMocks
    private TwitterQuery twitterQuery;

    @Mock
    private User user;

    @Mock
    private QueryResult queryResult;

    @Mock
    private Status status;

    @Test
    void should_search_on_twitter_and_return_tweet() throws TwitterException, JsonProcessingException {
        mapper = new ObjectMapper();
        var date = new Date();
        when(user.getName()).thenReturn("John");
        when(user.getCreatedAt()).thenReturn(date);
        when(user.getId()).thenReturn(1L);
        when(user.getScreenName()).thenReturn("j1");

        when(status.getCreatedAt()).thenReturn(date);
        when(status.getText()).thenReturn("test");
        when(status.getId()).thenReturn(2L);

        var author = Author.builder()
                .name("John")
                .createDate(date)
                .id(1L)
                .screenName("j1")
                .build();

        var message = TweetMessage.builder()
                .author(author)
                .createDate(date)
                .text("test")
                .id(2L)
                .build();

        var tweetText = mapper.writeValueAsString(message);

        when(status.getUser()).thenReturn(user);
        when(queryResult.getTweets()).thenReturn(List.of(status));
        when(twitter.search(Mockito.any())).thenReturn(queryResult);
        when(objectMapper.writeValueAsString(Mockito.any(TweetMessage.class))).thenReturn(tweetText);

        var result = twitterQuery.queryOnTwitter(twitter);

        Assertions.assertThat(result).isEqualTo(tweetText);

    }

    @Test
    void should_throws_an_exception_on_search_twitter() throws TwitterException, JsonProcessingException {
        when(twitter.search(Mockito.any())).thenThrow(new TwitterException("error test"));

        TwitterException thrown = assertThrows(TwitterException.class,
                () -> twitterQuery.queryOnTwitter(twitter));

        assertTrue(thrown.getMessage().contains("error test"));

    }

}
