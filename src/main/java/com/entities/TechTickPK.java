package com.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TechTickPK implements Serializable {
    private Integer techId;
    private Integer ticketId;

    public TechTickPK() {
    }

    public TechTickPK(Integer techId, Integer ticketId) {
        this.techId = techId;
        this.ticketId = ticketId;
    }

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object obj) {
        TechTickPK pk = (TechTickPK) obj;
        if (pk.techId.equals(this.techId) && pk.ticketId.equals(this.ticketId))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "TechTickPK{" +
                "techId=" + techId +
                ", ticketId=" + ticketId +
                '}';
    }
}
