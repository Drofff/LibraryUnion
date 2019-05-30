package com.appserve.Library.controller;

import com.appserve.Library.entity.Book;
import com.appserve.Library.repository.BookRepository;
import com.appserve.Library.repository.TypeRepository;
import com.appserve.Library.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
public class UserSearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TypeRepository typeRepository;

    @PostMapping("/quick")
    @ResponseBody
    public List<Book> findQuickResults(@RequestParam String query) {
        return bookRepository.searchByName(query);
    }

    @RequestMapping
    public String fullSearch(@RequestParam String query, @RequestParam(required = false) String author,
                             @RequestParam(required = false) Integer minRate, @RequestParam(required = false) Integer maxRate,
                             @RequestParam(required = false) String type, @RequestParam(required = false) String minYear,
                             @RequestParam(required = false) String maxYear, @RequestParam(required = false) String subscriptionType,
                             Model model) {

        List<Book> books = searchService.searchForABook(query);

        books = searchService.filterBooks(books, author, minRate, maxRate, type, minYear, maxYear, subscriptionType);

        model.addAttribute("books", books);

        model.addAttribute("lastQuery", query);
        model.addAttribute("lastAuthor", author);
        model.addAttribute("lastMinRate", minRate);
        model.addAttribute("lastMaxRate", maxRate);
        model.addAttribute("lastType", type);
        model.addAttribute("lastMinYear", minYear);
        model.addAttribute("lastMaxYear", maxYear);
        model.addAttribute("lastSubscriptionType", subscriptionType);

        model.addAttribute("types", typeRepository.findAll().stream().map(x -> x.getName()).collect(Collectors.toList()));

        return "searchResult";
    }

}
