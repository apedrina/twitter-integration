package com.alissonpedrina.producer.producer.service;

import com.alissonpedrina.producer.model.Author;
import com.alissonpedrina.producer.model.TweetMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.*;

@Service
@Slf4j
public class TwitterQuery {

    @Value("${twitter.lookup.string:java}")
    private String lookupByStringQuery;

    @Autowired
    private ObjectMapper objectMapper;

    public String queryOnTwitter(Twitter twitter) throws TwitterException {
        var messageToSend = "";
        try {
            log.debug("querying on twitter...");
            Query q = new Query(lookupByStringQuery);
            q.setCount(100);
            //these attributes were used cause rate limit
            //see more here: https://developer.twitter.com/en/docs/rate-limits
            //see more here: https://stackoverflow.com/questions/28656382/rate-limit-exceeded-with-twitter4j
            q.setResultType(Query.ResultType.recent);
            q.setLang("en");
            QueryResult queryResult = twitter.search(q);
            if (queryResult.getTweets().size() > 0) {
                for (Status tweet : queryResult.getTweets()) {
                    var tweetUser = tweet.getUser();
                    var author = Author.builder()
                            .name(tweetUser.getName())
                            .createDate(tweetUser.getCreatedAt())
                            .id(tweetUser.getId())
                            .screenName(tweetUser.getScreenName())
                            .build();
                    var message = TweetMessage.builder()
                            .author(author)
                            .createDate(tweet.getCreatedAt())
                            .text(tweet.getText())
                            .id(tweet.getId())
                            .build();
                    messageToSend = objectMapper.writeValueAsString(message);
                    log.info("twitter querying result: \n" + message.toString() + "\n");
                    return messageToSend;
                }

            }

        } catch (JsonProcessingException e) {
            log.error("was impossible parse to message to JSON: " + messageToSend);
            e.printStackTrace();

        }
        return null;

    }

}
