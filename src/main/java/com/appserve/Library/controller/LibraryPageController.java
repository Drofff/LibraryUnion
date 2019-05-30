package com.appserve.Library.controller;

import com.appserve.Library.entity.*;
import com.appserve.Library.repository.BookRepository;
import com.appserve.Library.repository.LibraryAccountRepository;
import com.appserve.Library.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/library")
@PreAuthorize("hasAuthority('LIBRARY')")
public class LibraryPageController {

    @Autowired
    LibraryAccountRepository libraryAccountRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TypeRepository typeRepository;

    @RequestMapping
    public String getLibraryMainPage(@AuthenticationPrincipal User user, Model model) {
        Library library = libraryAccountRepository.findByAccount(user);

        if (library == null) {
            return "errorPage";
        }

        if (!library.isActivated()) {
            model.addAttribute("message", "Account is waiting for activation");
            return "libraryAccountActivationStatus";
        }

        model.addAttribute("books", bookRepository.findByLibraryOwner(user));

        return "libraryMainPage";

    }

    @RequestMapping("/addBook")
    public String getAddBookPage(Model model) {
        List<String> subscriptions = new ArrayList<>();
        subscriptions.add(Subscription.REGULAR.toString());
        subscriptions.add(Subscription.PRIMARY.toString());

        model.addAttribute("subscriptionTypes", subscriptions);

        model.addAttribute("types", typeRepository.findAll().stream().map(x -> x.getName()).collect(Collectors.toList()));
        return "addNewBook";
    }

    @PostMapping("/addBook")
    public String addNewBook(@AuthenticationPrincipal User user, Model model, @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.getFieldErrors() == null || bindingResult.getFieldErrors().size() == 0 && book.getSubscriptionType() != null) {

            book.setRate(0);
            book.setLibraryOwner(user);

            bookRepository.save(book);

            model.addAttribute("message", "<i class='fas fa-check'></i> Book added");
            model.addAttribute("books", bookRepository.findByLibraryOwner(user));

            return "libraryMainPage";
        } else {
            model = parseErrors(book, bindingResult, model);
            return "addNewBook";
        }
    }

    public Model parseErrors(Book book, BindingResult bindingResult, Model model) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().stream().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
        model.mergeAttributes(errors);

        List<String> subscriptions = new ArrayList<>();
        subscriptions.add(Subscription.REGULAR.toString());
        subscriptions.add(Subscription.PRIMARY.toString());

        model.addAttribute("subscriptionTypes", subscriptions);

        model.addAttribute("types", typeRepository.findAll().stream().map(x -> x.getName()).collect(Collectors.toList()));

        if (book.getLibraryOwner() == null) {
            model.addAttribute("oldAuthor", book.getAuthor());
            model.addAttribute("oldDescription", book.getDescription());
            model.addAttribute("oldYear", book.getYear());
            model.addAttribute("oldType", book.getType());
            model.addAttribute("oldName", book.getName());
            model.addAttribute("oldSubscriptionType", book.getSubscriptionType());
            model.addAttribute("oldPhotoUrl", book.getPhotoUrl());
        } else {
            model.addAttribute("book", book);
        }
        return model;
    }



    @RequestMapping("/edit/{id}")
    public String editBook(Model model, @PathVariable String id, @AuthenticationPrincipal User user) {
        try {
            Optional<Book> book = bookRepository.findById(Long.parseLong(id));
            if (book.isPresent() && book.get().getLibraryOwner().getUsername().equals(user.getUsername())) {

                List<String> subscriptions = new ArrayList<>();
                subscriptions.add(Subscription.REGULAR.toString());
                subscriptions.add(Subscription.PRIMARY.toString());

                List<String> typesList = typeRepository.findAll().stream().map(x -> x.getName()).collect(Collectors.toList());

                model.addAttribute("types", typesList);

                model.addAttribute("subscriptionTypes", subscriptions);
                model.addAttribute("book", book.get());
                model.addAttribute("bookType", book.get().getType());
                model.addAttribute("subscriptionType", book.get().getSubscriptionType().getCurrentSubscription());
                return "editBookPage";
            }

            model.addAttribute("message", "Book with such id do not exists");
            model.addAttribute("books", bookRepository.findByLibraryOwner(user));

            return "libraryMainPage";
        } catch (Exception e) {
            return "redirect:/errorPage";
        }
    }

    @PostMapping("/edit/{id}")
    public String editBookMethod(Model model, @AuthenticationPrincipal User user, @Valid Book book, BindingResult bindingResult, @PathVariable String id) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(Long.parseLong(id));
            if (optionalBook.isPresent()) {

                Book oldBook = optionalBook.get();

                List<String> subscriptions = new ArrayList<>();
                subscriptions.add(Subscription.REGULAR.toString());
                subscriptions.add(Subscription.PRIMARY.toString());

                book.setLibraryOwner(oldBook.getLibraryOwner());
                book.setRate(oldBook.getRate());
                book.setId(oldBook.getId());

                if (bindingResult.getFieldErrors() == null || bindingResult.getFieldErrors().size() == 0 && book.getSubscriptionType() != null && book.getLibraryOwner().getUsername().equals(user.getUsername())) {
                    bookRepository.save(book);
                    model.addAttribute("message", "Book updated");
                    model.addAttribute("books", bookRepository.findByLibraryOwner(user));
                    return "libraryMainPage";
                } else {

                    model.addAttribute("subscriptionTypes", subscriptions);
                    model.addAttribute("types", typeRepository.findAll().stream().map(x -> x.getName()).collect(Collectors.toList()));
                    model = parseErrors(book, bindingResult, model);
                    return "editBookPage";
                }
            } else {
                model.addAttribute("message", "Book with such id do not exists");
                model.addAttribute("books", bookRepository.findByLibraryOwner(user));

                return "libraryMainPage";
            }
        } catch (Exception e) {
            return "redirect:/errorPage";
        }
    }

    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id, @AuthenticationPrincipal User user) {

        try {

            Optional<Book> bookOptional = bookRepository.findById(Long.parseLong(id));

            if (bookOptional.isPresent() && bookOptional.get().getLibraryOwner().getUsername().equals(user.getUsername())) {
                bookRepository.delete(bookOptional.get());
            }
            return "redirect:/library";

        } catch (Exception e) {
            return "redirect:/errorPage";
        }
    }

}
