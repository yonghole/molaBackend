package com.mola.mola.domain;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class OutSourceInbound {
    private Long id;
    private Long user_id;

    @Temporal(TemporalType.DATE)
    private Date creation_date;

    private String requirements;

    public Long getid() {
        return id;
    }

    public void setid(Long oid) {
        this.id = oid;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getcreation_date() {
        return creation_date;
    }

    public void setcreation_date(Date outsource_creation_date) {
        this.creation_date = outsource_creation_date;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}
