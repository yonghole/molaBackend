package com.mola.mola.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "WorkHistory",
uniqueConstraints = @UniqueConstraint(
        columnNames={"image_id"}
))
public class WorkHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "work_time")
    LocalDateTime workTime;

    @Column(name = "is_rejected")

    Boolean isRejected;

}
