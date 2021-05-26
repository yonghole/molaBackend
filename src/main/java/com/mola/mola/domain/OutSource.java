package com.mola.mola.domain;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class OutSource {
    private int id;
    private int user_id;

    @Temporal(TemporalType.DATE)
    private Date creation_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
}
