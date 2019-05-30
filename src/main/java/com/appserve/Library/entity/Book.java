package com.appserve.Library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Book must have a name")
    private String name;

    @NotBlank(message = "Who is author of this book?")
    private String author;

    @NotBlank(message = "Describe this book please")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id")
    private LibraryCard holderId;

    private int rate;

    @NotBlank(message = "Select book type, please")
    private String type;

    @NotBlank(message = "When was it published?")
    private String year;

    private LocalDate dateWhenTaken;

    @NotBlank(message = "Please, provide photo")
    private String photoUrl;

    private Subscription subscriptionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id")
    private User libraryOwner;

    public Book() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

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
