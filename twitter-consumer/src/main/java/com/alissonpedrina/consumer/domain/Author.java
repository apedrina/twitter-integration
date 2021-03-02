package com.alissonpedrina.consumer.domain;

import com.alissonpedrina.consumer.util.DateUtil;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "create_date")
    private Date createDate;

    @Column
    private String name;

    @Column(name = "screen_name")
    private String screenName;

    @Transient
    private List<Message> messages;

    public List<Message> getMessages() {
        if (Objects.isNull(messages)) {
            messages = new ArrayList<>();

        }
        return messages;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return userId == author.userId && Objects.equals(name, author.name) && Objects.equals(screenName, author.screenName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, screenName);
    }


    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", userId=" + userId +
                ", createDate=" + DateUtil.parseToEpochMilli(createDate) +
                ", name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                ", messages=" + messages +
                '}';
    }
}
