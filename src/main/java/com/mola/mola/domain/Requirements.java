package com.mola.mola.domain;

import javax.persistence.*;

@Entity
@Table(name = "Requirements")
public class Requirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long os_id;


    @Column(name = "contents")
    private String requirements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOs_id() {
        return os_id;
    }

    public void setOs_id(Long os_id) {
        this.os_id = os_id;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}
