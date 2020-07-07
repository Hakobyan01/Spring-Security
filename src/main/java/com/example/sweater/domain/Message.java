package com.example.sweater.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text")
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message to long (more then 2kb)")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "filename")
    private String fileName;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(String text, User auther) {
        this.text = text;
        this.author = auther;
    }

    public Message(String text, User auther, String fileName) {
        this.text = text;
        this.author = auther;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
                ", message='" + text + '\'' +
                ", auther=" + author +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}

