package com.alissonpedrina.producer.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

@Configuration
@ConditionalOnProperty("spring.activemq.broker-url")
@EnableJms
@Slf4j
public class ProducerConfig {

    private static final List<String> TRUSTED_PACKAGES = List.of(
            "com.alissonpedrina",
            "java");

    @Value("${spring.activemq.broker-url}")
    String brokerUrl;

    @Bean
    ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        log.info("Configuring ActiveMQ Sender Connection Factory.");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setTrustedPackages(TRUSTED_PACKAGES);
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setSendAcksAsync(true);
        return activeMQConnectionFactory;
    }

    @Bean
    CachingConnectionFactory cachingConnectionFactory() {
        log.info("Configuring ActiveMQ Caching Connection Factory.");
        return new CachingConnectionFactory(senderActiveMQConnectionFactory());
    }

    @Bean
    JmsTemplate jmsTemplate() {
        log.info("Providing JMS Template.");
        return new JmsTemplate(cachingConnectionFactory());
    }
}

