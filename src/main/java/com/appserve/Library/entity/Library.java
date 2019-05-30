package com.appserve.Library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Customer need to know your address to get reserved books!")
    private String address;

    @NotBlank(message = "You must provide name")
    private String name;

    private boolean activated = false;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "provided_books")
    private List<Book> providedBooks = new LinkedList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private User account;

    private String documentUrl;

    @NotBlank(message = "We may contact you to ask some additional information")
    private String phoneNumber;

    @NotBlank(message = "We would like to see your place. Customers too")
    private String photoUrl;

    public Library() {}

    public Library(Long id, String address, String phoneNumber, String name, List<Book> providedBooks, User account, String documentUrl, String photoUrl) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.providedBooks = providedBooks;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.documentUrl = documentUrl;
        this.photoUrl = photoUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getProvidedBooks() {
        return providedBooks;
    }

    public void setProvidedBooks(List<Book> providedBooks) {
        this.providedBooks = providedBooks;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
