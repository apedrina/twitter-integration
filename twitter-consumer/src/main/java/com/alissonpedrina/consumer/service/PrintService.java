package com.alissonpedrina.consumer.service;

import com.alissonpedrina.consumer.domain.Author;
import com.alissonpedrina.consumer.domain.Message;
import com.alissonpedrina.consumer.repo.MessageRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PrintService {

    @Autowired
    private MessageRepository messageRepository;

    @Scheduled(fixedRate = 10000)
    public void isUpTo100MessagesThenPrint() {
        log.info("isUpTo100MessagesThenPrint schedule running...");
        if (messageRepository.findAll().stream().filter(message -> "0".equals(message.getIsPrinted()))
                .count() > 100) {
            log.info("isUpTo100MessagesThenPrint calling print()");
            TweetScheduleService.justDone = true;
            print();

        }

    }

    public List<Author> print() {
        log.info("starting print()");
        var printResult = new Object() {
            List result = new ArrayList<Author>();

        };
        var messages = messageRepository.findAll().stream()
                .filter(message -> "0".equals(message.getIsPrinted())).collect(Collectors.toList());
        var authors = new HashSet<Author>();
        messages.forEach(message -> {
            authors.add(message.getAuthor());

        });
        messages.forEach(message -> {
            authors.forEach(author -> {
                if (author.equals(message.getAuthor())) {
                    author.getMessages().add(message);
                }
            });

        });
        var sortedAuthors = authors.stream()
                .sorted(Comparator.comparing(
                        Author::getCreateDate))
                .collect(Collectors.toList());

        sortedAuthors.forEach(author -> {
            author.getMessages().stream()
                    .sorted(Comparator.comparing(Message::getCreateDate));
            System.out.println(author);
            log.info("print message: " + author);
            printResult.result.add(author);

        });
        return printResult.result;

    }

}
