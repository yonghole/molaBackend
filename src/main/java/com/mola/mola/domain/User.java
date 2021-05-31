package com.mola.mola.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String name;
    private String password;
    private int point;
    private String phoneNum;

    @OneToMany(mappedBy = "user")
    private List<OutSource> outSources;

}
