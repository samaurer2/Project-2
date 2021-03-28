package com.dtos;

import com.entities.Priority;
import com.entities.Ticket;

public class TicketDto {

    private Integer ticketId;

    private String description;

    private int priority;

    private Long epochStart;

    private Long epochEnd;

    private Integer clientId;

    public TicketDto (){}

    public TicketDto (Ticket ticket){
        this.ticketId = ticket.getTicketId();
        this.description = ticket.getDescription();
        this.priority = ticket.getPriority().ordinal();
        this.epochStart = ticket.getEpochStart();
        this.epochEnd = ticket.getEpochEnd();
        this.clientId = ticket.getClientId();
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority.ordinal();
    }

    public Long getEpochStart() {
        return epochStart;
    }

    public void setEpochStart(Long epochStart) {
        this.epochStart = epochStart;
    }

    public Long getEpochEnd() {
        return epochEnd;
    }

    public void setEpochEnd(Long epochEnd) {
        this.epochEnd = epochEnd;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", epochStart=" + epochStart +
                ", epochEnd=" + epochEnd +
                ", clientId=" + clientId +
                '}';
    }
}
