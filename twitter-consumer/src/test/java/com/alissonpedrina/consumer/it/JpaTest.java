package com.alissonpedrina.consumer.it;

import com.alissonpedrina.consumer.domain.Audit;
import com.alissonpedrina.consumer.domain.Author;
import com.alissonpedrina.consumer.domain.Message;
import com.alissonpedrina.consumer.repo.AuditRepository;
import com.alissonpedrina.consumer.repo.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JpaTest extends AbstractIT {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AuditRepository auditRepository;

    private Long messageId;

    @BeforeAll
    public void setUp() {
        var author = Author.builder()
                .name("User test")
                .createDate(new Date())
                .userId(1L)
                .screenName("User twitter")
                .build();
        var message = Message.builder()
                .text("""
                        Work at Sytac will be awesome
                        lets try do my best
                        """)
                .author(author)
                .createDate(new Date())
                .build();

        messageRepository.save(message);
        messageId = message.getId();

    }

    @Test
    void should_create_one_message() {
        int expectedSize = 1;

        Assertions.assertEquals(expectedSize, messageRepository.findAll().size());

    }

    @Test
    void should_create_one_audit_record() {
        int expectedSize = 1;

        Assertions.assertEquals(expectedSize, messageRepository.findAll().size());

        Audit audit = Audit.builder()
                .harvestDate(new Date())
                .messageId(messageId)
                .build();

        auditRepository.save(audit);

        Assertions.assertNotNull(audit.getId());

    }

}
