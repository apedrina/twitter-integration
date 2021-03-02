package com.alissonpedrina.producer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetMessage {

    private Long id;

    private Date createDate;

    private String text;

    private Author author;


}
