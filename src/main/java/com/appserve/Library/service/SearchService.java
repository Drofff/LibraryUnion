package com.appserve.Library.service;

import com.appserve.Library.entity.Book;
import com.appserve.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> searchForABook(String query) {
        return bookRepository.search(query);
    }

    public List<Book> filterBooks(List<Book> books, String author, Integer minRate, Integer maxRate, String type, String minYear, String maxYear, String subscriptionType) {

        if (author != null && !author.isEmpty()) {
            books = books.stream().filter(x -> x.getAuthor().matches(".*(" + author + ").*")).collect(Collectors.toList());
        }

        if (minRate != null && maxRate != null) {

            books = books.stream().filter(x -> x.getRate() >= minRate && x.getRate() <= maxRate).collect(Collectors.toList());

        } else if (minRate != null) {

            books = books.stream().filter(x -> x.getRate() >= minRate).collect(Collectors.toList());

        } else if (maxRate != null) {

            books = books.stream().filter(x -> x.getRate() <= maxRate).collect(Collectors.toList());

        }

        if (type != null && !type.isEmpty()) {
            books = books.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
        }

        try {

            if (minYear != null && maxYear != null && !minYear.isEmpty() && !maxYear.isEmpty()) {

                LocalDate minDate = LocalDate.parse(minYear);
                LocalDate maxDate = LocalDate.parse(maxYear);
                books = books.stream().filter(x -> LocalDate.parse(x.getYear()).isAfter(minDate) && LocalDate.parse(x.getYear()).isBefore(maxDate)).collect(Collectors.toList());

            } else if (minYear != null && !minYear.isEmpty()) {

                LocalDate minDate = LocalDate.parse(minYear);
                books = books.stream().filter(x -> LocalDate.parse(x.getYear()).isAfter(minDate)).collect(Collectors.toList());

            } else if (maxYear != null && !maxYear.isEmpty()) {

                LocalDate maxDate = LocalDate.parse(maxYear);
                books = books.stream().filter(x -> LocalDate.parse(x.getYear()).isBefore(maxDate)).collect(Collectors.toList());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (subscriptionType != null && !subscriptionType.isEmpty()) {
            books = books.stream().filter(x -> x.getSubscriptionType().getCurrentSubscription().equals(subscriptionType)).collect(Collectors.toList());
        }

        return books;
    }

}
