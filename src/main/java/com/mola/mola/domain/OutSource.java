package com.mola.mola.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Outsource")
@TableGenerator(name = "ORDER_SEQ_GENERATOR", table = "TB_SEQUENCE",pkColumnValue = "ORDER_SEQ", allocationSize = 1)
public class OutSource {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ORDER_SEQ_GENERATOR")
    private Long id;
    private Long user_id;

    @Temporal(TemporalType.DATE)
    @Column(name = "outsource_creation_date")
    private Date outsource_creation_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getCreation_date() {
        return outsource_creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.outsource_creation_date = creation_date;
    }
}
