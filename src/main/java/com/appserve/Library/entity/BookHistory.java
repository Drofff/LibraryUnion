package com.appserve.Library.entity;

import javax.persistence.*;

@Entity
public class BookHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book bookId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id")
    private User holderId;

    private String holderName;

    private String dateOfStart;

    private String dateOfEnd;

    private Integer rate;

    public BookHistory() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public User getHolderId() {
        return holderId;
    }

    public void setHolderId(User holderId) {
        this.holderId = holderId;
    }

    public String getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(String dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public String getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(String dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
