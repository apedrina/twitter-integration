package com.alissonpedrina.consumer.service;

import com.alissonpedrina.consumer.domain.Author;
import com.alissonpedrina.consumer.domain.Message;
import com.alissonpedrina.consumer.repo.MessageRepository;
import com.alissonpedrina.consumer.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrintServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private PrintService printService;

    @Test
    void should_print_messages() {
        var date = new Date();
        var epoch = DateUtil.parseToEpochMilli(date);
        var expected = String.format("Author{id=0, userId=1, createDate=%s, name='john', screenName='jh1'", epoch);

        when(messageRepository.findAll()).thenReturn(createListWithJustOneAuthor(101, date));

        List<Author> authors = printService.print();

        assertTrue(authors.get(0).toString().contains(expected));

    }

    @Test
    void should_print_messages_sorted_by_authors() {
        var date = new Date();
        var messages = createListWithJustOneAuthor(101, date);

        LocalDate oldDateLocalDate = LocalDate.now().minusYears(3);
        Date oldDate = java.sql.Date.valueOf(oldDateLocalDate);
        messages.add(createMessageByDate(oldDate));

        var epoch_index0 = DateUtil.parseToEpochMilli(oldDate);
        var expected_index0 = String.format("Author{id=0, userId=3, createDate=%s, name='Mary', screenName='mary', messages=[Message{id=null, createDate=%s, text='new text\n" +
                ", author=0, isPrinted=0}]}", epoch_index0, epoch_index0);

        when(messageRepository.findAll()).thenReturn(messages);

        List<Author> authorsProcessed = printService.print();

        assertThat(authorsProcessed.get(0).toString()).isEqualTo(expected_index0);

    }

    @Test
    void should_call_print() {
        TweetScheduleService.justDone = false;

        when(messageRepository.findAll()).thenReturn(createListWithJustOneAuthor(101, new Date()));

        printService.isUpTo100MessagesThenPrint();

        assertTrue(TweetScheduleService.justDone);

    }

    @Test
    void should_not_call_print() {
        TweetScheduleService.justDone = false;

        when(messageRepository.findAll()).thenReturn(createListWithJustOneAuthor(2, new Date()));

        printService.isUpTo100MessagesThenPrint();

        assertFalse(TweetScheduleService.justDone);

    }

    private List<Message> createListWithJustOneAuthor(int number, Date date) {
        var list = new ArrayList<Message>();
        for (int i = 0; i < number; i++) {
            var author = Author.builder()
                    .userId(1L)
                    .screenName("jh1")
                    .name("john")
                    .createDate(date)
                    .build();
            var message = Message.builder()
                    .createDate(date)
                    .text("test text")
                    .author(author).isPrinted("0").build();
            list.add(message);

        }
        return list;
    }

    private Message createMessageByDate(Date date) {
        var oldAuthor = Author.builder()
                .userId(3L)
                .screenName("mary")
                .name("Mary")
                .createDate(date)
                .build();

        return Message.builder()
                .text("new text")
                .isPrinted("0")
                .createDate(date)
                .author(oldAuthor)
                .build();

    }

}
