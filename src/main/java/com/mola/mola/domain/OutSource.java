package com.mola.mola.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Outsource")
public class OutSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outsource_id")
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    User user;

    @OneToMany(mappedBy = "outSource")
    @JsonManagedReference
    List<Image> imageList;

    private String requirements;
    private Long imgTotal;
    private Long imgCompleted;
    private Long credit;
    private String title;

    @Column(name = "outsource_creation_date")
    private LocalDate outsourceCreationDate;

    public void addImage(Image image){
        image.setOutSource(this);
        this.imageList.add(image);
    }
}
