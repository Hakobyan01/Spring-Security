package com.example.sweater.domain;

import org.springframework.beans.factory.annotation.Value;

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

    @Column(name = "filename")
    private String fileName;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public Message(String message, User auther) {
        this.message = message;
        this.auther = auther;
    }

    public Message(String message, User auther, String fileName) {
        this.message = message;
        this.auther = auther;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", auther=" + auther +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}

