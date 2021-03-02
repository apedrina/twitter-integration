package com.alissonpedrina.consumer.service;

import com.alissonpedrina.consumer.domain.Audit;
import com.alissonpedrina.consumer.domain.Author;
import com.alissonpedrina.consumer.domain.Message;
import com.alissonpedrina.consumer.repo.AuditRepository;
import com.alissonpedrina.consumer.repo.MessageRepository;
import com.alissonpedrina.consumer.util.QueueAddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Test
    void should_save_message() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var date = new Date();
        var author = Author.builder()
                .createDate(date)
                .name("John")
                .screenName("jh1")
                .userId(2L)
                .build();
        var messageDomain = Message.builder()
                .createDate(new Date())
                .text("@John 123")
                .author(author)
                .build();
        var message = mapper.writeValueAsString(messageDomain);

        when(objectMapper.readValue(message, Message.class)).thenReturn(messageDomain);
        when(messageRepository.save(any(Message.class))).thenReturn(new Message());
        when(auditRepository.save(any(Audit.class))).thenReturn(new Audit());

        kafkaConsumer.consume(message);

        verify(messageRepository, times(1)).save(any());
        verify(auditRepository, times(1)).save(any());

    }

    @Test
    void should_send_message_to_recovery() throws JsonProcessingException {
        var message = "test";
        when(objectMapper.readValue(message, Message.class)).thenThrow(new JsonMappingException("error test"));

        kafkaConsumer.consume(message);

        verify(jmsTemplate, times(1)).convertAndSend(message, QueueAddress.RECOVERY_QUEUE);

    }
}
