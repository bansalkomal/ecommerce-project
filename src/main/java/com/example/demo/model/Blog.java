package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String excerpt;

    @Column(columnDefinition = "TEXT")
    private String details;

    private String image;
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    // Constructors
    public Blog() {
        this.date = new Date(); // Set default date to current timestamp
    }

    public Blog(String title, String excerpt, String details, String image, String author) {
        this.title = title;
        this.excerpt = excerpt;
        this.details = details;
        this.image = image;
        this.author = author;
        this.date = new Date();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
