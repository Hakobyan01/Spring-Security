package com.example.sweater.domain;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User auther;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public Message(String message, User auther) {
        this.message = message;
        this.auther = auther;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getAuther() {
        return auther;
    }

    public void setAuther(User auther) {
        this.auther = auther;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", auther=" + auther +
                '}';
    }
}

