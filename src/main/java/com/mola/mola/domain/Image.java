package com.mola.mola.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "outsource_id", referencedColumnName = "outsource_id")
    private OutSource outSource;
}
