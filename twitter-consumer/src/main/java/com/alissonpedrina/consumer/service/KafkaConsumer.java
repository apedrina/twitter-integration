package com.alissonpedrina.consumer.service;

import com.alissonpedrina.consumer.domain.Audit;
import com.alissonpedrina.consumer.domain.Message;
import com.alissonpedrina.consumer.repo.AuditRepository;
import com.alissonpedrina.consumer.repo.MessageRepository;
import com.alissonpedrina.consumer.util.QueueAddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @KafkaListener(topics = "twitter")
    public void consume(String message) {
        try {
            log.debug("receiving message: " + message);
            var messageDomain = objectMapper.readValue(message, Message.class);
            messageDomain.setIsPrinted("0");
            messageRepository.save(messageDomain);
            auditRepository.save(Audit.builder()
                    .harvestDate(new Date())
                    .messageId(messageDomain.getId())
                    .build());
            log.debug("saving message: " + messageDomain);

        } catch (JsonMappingException e) {
            log.error("json mapping error on message: " + message);
            jmsTemplate.convertAndSend(message, QueueAddress.RECOVERY_QUEUE);
            e.printStackTrace();

        } catch (JsonProcessingException e) {
            log.error("json parsing error on message: " + message);
            jmsTemplate.convertAndSend(message, QueueAddress.RECOVERY_QUEUE);
            e.printStackTrace();

        }

    }

}
