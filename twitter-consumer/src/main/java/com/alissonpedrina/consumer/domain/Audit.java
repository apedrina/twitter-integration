package com.alissonpedrina.consumer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "harvest_date")
    private Date harvestDate;

}
