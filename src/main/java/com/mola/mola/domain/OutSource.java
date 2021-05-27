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
    private String requirements;
    private Long img_total;
    private Long img_completed;
    private Long credit;
    private String title;

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Long getImg_total() {
        return img_total;
    }

    public void setImg_total(Long img_total) {
        this.img_total = img_total;
    }

    public Long getImg_completed() {
        return img_completed;
    }

    public void setImg_completed(Long img_completed) {
        this.img_completed = img_completed;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getOutsource_creation_date() {
        return outsource_creation_date;
    }

    public void setOutsource_creation_date(Date outsource_creation_date) {
        this.outsource_creation_date = outsource_creation_date;
    }

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
