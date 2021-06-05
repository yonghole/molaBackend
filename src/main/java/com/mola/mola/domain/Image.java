package com.mola.mola.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name = "outsource_id", referencedColumnName = "outsource_id")
    private OutSource outSource;

    @Column(name  = "y_coordinate")
    private Double yCoordinate;

    @Column(name  = "x_coordinate")
    private Double xCoordinate;

    @Column(name = "height")
    private Double height;

    @Column(name = "width")
    private Double width;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private WorkHistory workHistory;
}
