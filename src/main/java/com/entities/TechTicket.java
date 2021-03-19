package com.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tech_ticket")
public class TechTicket {

    @Id
    private TechTickPK pk;

    public TechTicket() {
    }

    public TechTicket(TechTickPK pk) {
        this.pk = pk;
    }

    public TechTickPK getPk() {
        return pk;
    }

    public void setPk(TechTickPK pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "TechTicket{" +
                "pk=" + pk +
                '}';
    }
}
