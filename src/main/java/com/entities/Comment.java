package com.entities;

import javax.persistence.*;

@Entity
@Table(name ="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(name = "tech_id")
    private Integer techId;

    @Column(name="client_id")
    private Integer clientId;

    @Column(name = "comment", nullable = false, updatable = false)
    private String comment;

    @Column(name = "date_submitted")
    private Long epochTime;

    @Column(name = "ticket_id")
    @JoinColumn(name = "ticket_id")
    private Integer ticketId;

    public Comment() {
    }

    public Comment(String comment, Integer ticketId) {
        this.comment = comment;
        this.ticketId = ticketId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(Long epochTime) {
        this.epochTime = epochTime;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", techId=" + techId +
                ", clientId=" + clientId +
                ", comment='" + comment + '\'' +
                ", epochTime=" + epochTime +
                ", ticketId=" + ticketId +
                '}';
    }
}
