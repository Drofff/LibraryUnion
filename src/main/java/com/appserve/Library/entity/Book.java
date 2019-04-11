package com.appserve.Library.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String author;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id")
    private LibraryCard holderId;

    private int rate;

    private LocalDate dateWhenTaken;

    private String photoUrl;

    private Subscription subscriptionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id")
    private User libraryOwner;

    public Book() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibraryCard getHolderId() {
        return holderId;
    }

    public void setHolderId(LibraryCard holderId) {
        this.holderId = holderId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public LocalDate getDateWhenTaken() {
        return dateWhenTaken;
    }

    public void setDateWhenTaken(LocalDate dateWhenTaken) {
        this.dateWhenTaken = dateWhenTaken;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Subscription getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Subscription subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public User getLibraryOwner() {
        return libraryOwner;
    }

    public void setLibraryOwner(User libraryOwner) {
        this.libraryOwner = libraryOwner;
    }
}
