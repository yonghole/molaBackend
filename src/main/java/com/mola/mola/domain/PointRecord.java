package com.mola.mola.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "point_record")
public class PointRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    User user;

    private String changeType;
    @Column(name = "BeforeP")
    private Integer pointBefore;
    @Column(name = "AfterP")
    private Integer pointAfter;
    @Column(name = "modified_date")
    private LocalDateTime pointChangeDate;
}
