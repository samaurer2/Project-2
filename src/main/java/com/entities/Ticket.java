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

    //Todo many-to-many annotation
//    private List<Integer> technicians;

    @Column(name="c_id", nullable = false)
    private Integer clientId;

    public Ticket() {
    }

    public Ticket(String description, Integer clientId) {
        this.description = description;
        this.clientId = clientId;
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

//    public List<Integer> getTechnicians() {
//        return technicians;
//    }
//
//    public void setTechnicians(List<Integer> technicians) {
//        this.technicians = technicians;
//    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

//    @Override
//    public String toString() {
//        return "Ticket{" +
//                "ticketId=" + ticketId +
//                ", description='" + description + '\'' +
//                ", priority=" + priority +
//                ", technicians=" + technicians +
//                ", clientId=" + clientId +
//                '}';
//    }
}
