package com.mola.mola.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private WorkHistory() {}

    public static WorkHistory withTimeOf(Image image, User user){

        WorkHistory workHistory = new WorkHistory();
        workHistory.setImage(image);
        workHistory.setIsRejected(false);
        workHistory.setUser(user);
        workHistory.setWorkTime(LocalDateTime.now());

        return workHistory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @JsonManagedReference
    private Image image;

    @Column(name = "work_time")
    LocalDateTime workTime;

    @Column(name = "is_rejected")
    Boolean isRejected = true;

}
