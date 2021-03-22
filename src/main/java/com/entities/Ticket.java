package com.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_id")
    private Integer ticketId;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="priority")
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("LOW")
    private Priority priority;

    @Column(name = "date_submitted")
    private Long epochStart;

    @Column(name = "date_completed")
    private Long epochEnd;

    @OneToMany(mappedBy = "ticketId", cascade = CascadeType.ALL)
    List<Comment> comments;

    @Column(name="c_id")
    private Integer clientId;

    public Ticket() {
    }

    public Ticket(String description, Integer clientId) {
        this.description = description;
        this.clientId = clientId;
        this.priority = Priority.LOW;
    }

    public Ticket(Integer ticketId, String description, Integer clientId) {
        this.ticketId = ticketId;
        this.description = description;
        this.clientId = clientId;
        this.priority = Priority.LOW;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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



}
