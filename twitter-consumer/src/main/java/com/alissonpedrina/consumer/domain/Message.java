package com.alissonpedrina.consumer.domain;

import com.alissonpedrina.consumer.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "text")
    private String text;

    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.ALL)
    private Author author;

    @Column(name = "is_printed")
    private String isPrinted;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", createDate="
                + (Objects.nonNull(createDate) ? DateUtil.parseToEpochMilli(createDate) : "") +
                ", text='" + text + "\n" +
                ", author=" + author.getId() +
                ", isPrinted=" + isPrinted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return createDate.equals(message.createDate) && text.equals(message.text) && author.equals(message.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, text, author);
    }
}
