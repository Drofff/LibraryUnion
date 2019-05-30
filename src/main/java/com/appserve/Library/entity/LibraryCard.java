package com.appserve.Library.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please, enter your first name")
    @Length(max = 40, message = "Max length is 40")
    private String firstName;

    @NotBlank(message = "Set your last name, please")
    @Length(max = 40, message = "Max length is 40")
    private String lastName;

    private String dateOfBirthday;

    @NotBlank(message = "Address is very valuable for libraries")
    @Length(max = 70, message = "Max length is 70")
    private String address;

    private String photoUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "books_taken")
    private List<Book> booksTaken = new LinkedList<>();

    @CollectionTable(name = "subscription", joinColumns = {@JoinColumn(name = "card_subscription")})
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Subscription.class)
    @Enumerated(EnumType.STRING)
    private Set<Subscription> cardSubscriptions = new HashSet<>();

    public LibraryCard() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirthday() {
        return dateOfBirthday;
    }

    public void setDateOfBirthday(String dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<Book> getBooksTaken() {
        return booksTaken;
    }

    public void setBooksTaken(List<Book> booksTaken) {
        this.booksTaken = booksTaken;
    }

    public Set<Subscription> getCardSubscriptions() {
        return cardSubscriptions;
    }

    public void setCardSubscriptions(Set<Subscription> cardSubscriptions) {
        this.cardSubscriptions = cardSubscriptions;
    }




}
