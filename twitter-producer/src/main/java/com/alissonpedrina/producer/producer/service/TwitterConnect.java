package com.alissonpedrina.producer.producer.service;

import com.alissonpedrina.producer.exception.TwitterAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import static java.lang.String.format;

@Slf4j
@Service
public class TwitterConnect {

    public static boolean authenticated = false;

    public static Twitter twitter;

    private static String CONSUMER_KEY = "RLSrphihyR4G2UxvA0XBkLAdl";

    private static String CONSUMER_SECRET = "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4";

    public static void connect() throws URISyntaxException, TwitterAuthenticationException {
        RequestToken requestToken = null;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(null)
                .setOAuthAccessTokenSecret(null);
        TwitterFactory tf = new TwitterFactory(cb.build());

        twitter = tf.getInstance();
        try {
            requestToken = twitter.getOAuthRequestToken();

        } catch (Exception e) {
            var errorMsg = format("error on requesting token: %s", e.getMessage());
            log.error(errorMsg);
            throw new TwitterAuthenticationException(errorMsg);

        }
        var uri = new URI(requestToken.getAuthorizationURL());
        var pin = "";
        var scanner = new Scanner(System.in);
        try {
            System.out.println("Go to the following link in your browser:\n" + uri.toString());
            System.out.println("\nPlease enter the retrieved PIN:");
            pin = scanner.nextLine();

        } finally {
            scanner.close();

        }
        if (pin == null) {
            throw new TwitterAuthenticationException("Unable to read entered PIN");

        }
        AccessToken token = null;
        try {
            token = twitter.getOAuthAccessToken(requestToken, pin);

        } catch (TwitterException e) {
            var errorMsg = format("Twitter exception: ", e.getMessage());
            log.error(errorMsg);
            throw new TwitterAuthenticationException(errorMsg);

        }
        twitter.setOAuthAccessToken(token);
        log.info(format("Token key=%s secret=%s\n", token.getToken(), token.getTokenSecret()));
        authenticated = true;

    }

}
