package com.alissonpedrina.consumer.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.Session;
import java.util.List;

/**
 * Receiver config is the class that configures activemq if the property
 * broker-url is configured in your application yml. For security reasons we
 * should detail what kind of packages we can use in activemq, in the method
 * setTrustedPackages.
 *
 * @author anibal fernandes
 */
@Configuration
@ConditionalOnProperty("spring.activemq.broker-url")
@EnableJms
public class ReceiverConfig {

    private static final List<String> TRUSTED_PACKAGES = List.of(
            "com.alissonpedrina",
            "java");

    @Value("${spring.activemq.broker-url}")
    String brokerUrl;

    @Bean
    ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setTrustedPackages(TRUSTED_PACKAGES);
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setSendAcksAsync(true);
        return activeMQConnectionFactory;
    }

    @Bean
    DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(receiverActiveMQConnectionFactory());
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }
}
