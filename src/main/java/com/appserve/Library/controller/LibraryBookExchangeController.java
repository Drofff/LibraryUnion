package com.appserve.Library.controller;

import com.appserve.Library.entity.*;
import com.appserve.Library.repository.BookHistoryRepository;
import com.appserve.Library.repository.BookRepository;
import com.appserve.Library.repository.LibraryAccountRepository;
import com.appserve.Library.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/library")
@PreAuthorize("hasAuthority('LIBRARY')")
public class LibraryBookExchangeController {

    @Autowired
    LibraryAccountRepository libraryAccountRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookHistoryRepository bookHistoryRepository;

    @RequestMapping("/operations")
    public String doOperationsPage(@AuthenticationPrincipal User user, Model model) {
        List<Book> books = bookRepository.findByLibraryOwner(user);
        model.addAttribute("books", books.stream().filter(x -> x.getHolderId() != null).collect(Collectors.toList()));
        return "libraryOperationsPage";
    }

    @RequestMapping("/give/{id}")
    public String giveBookToCustomer(@AuthenticationPrincipal User user, @PathVariable String id, Model model) {
        try {
            Optional<Book> book = bookRepository.findById(Long.parseLong(id));
            if (book.isPresent() && book.get().getLibraryOwner().getUsername().equals(user.getUsername()) && book.get().getDateWhenTaken() == null) {
                Book realBook = book.get();
                realBook.setDateWhenTaken(LocalDate.now());
                bookRepository.save(realBook);
                model.addAttribute("message", "Done! Customer have to return book till " + LocalDate.now().plusWeeks(1).toString());
                List<Book> books = bookRepository.findByLibraryOwner(user);
                model.addAttribute("books", books.stream().filter(x -> x.getHolderId() != null).collect(Collectors.toList()));
                return "libraryOperationsPage";
            }

            return "redirect:/errorPage";

        } catch (Exception e) {
            return "redirect:/errorPage";
        }
    }

    @RequestMapping("/receive")
    public String receiveBookGet() {
        return "redirect:/";
    }

    @PostMapping("/receive")
    public String receiveBook(@AuthenticationPrincipal User user, Model model, @RequestParam Long rate, @RequestParam Long id, @RequestParam Long trueId) {
        try {
            Optional<Book> book = bookRepository.findById(id);
            if (book.isPresent() && id == trueId) {
                if (book.get().getLibraryOwner().getUsername().equals(user.getUsername()) && book.get().getDateWhenTaken() != null) {

                    Book realBook = book.get();

                    if (rate < 1 || rate > 10) {
                        model.addAttribute("message", "Rate must be in range from 1 to 10");
                        List<Book> books = bookRepository.findByLibraryOwner(user);
                        model.addAttribute("books", books.stream().filter(x -> x.getHolderId() != null).collect(Collectors.toList()));
                        return "libraryOperationsPage";
                    }

                    BookHistory bookHistory = new BookHistory();
                    bookHistory.setBookId(realBook);
                    bookHistory.setDateOfStart(realBook.getDateWhenTaken().toString());
                    bookHistory.setDateOfEnd(LocalDate.now().toString());
                    bookHistory.setHolderId(realBook.getHolderId().getUserId());
                    LibraryCard libraryCard = libraryRepository.findByUserId(realBook.getHolderId().getUserId());
                    bookHistory.setHolderName(libraryCard.getFirstName() + " " + libraryCard.getLastName());
                    bookHistory.setRate(rate.intValue());
                    bookHistoryRepository.save(bookHistory);

                    Long avgRate = realBook.getRate() + rate / 2;
                    realBook.setRate(avgRate.intValue());
                    realBook.setDateWhenTaken(null);
                    realBook.setHolderId(null);
                    bookRepository.save(realBook);
                    model.addAttribute("message", "Done! Thank you!");
                    List<Book> books = bookRepository.findByLibraryOwner(user);
                    model.addAttribute("books", books.stream().filter(x -> x.getHolderId() != null).collect(Collectors.toList()));
                    return "libraryOperationsPage";
                }
            } else {
                model.addAttribute("message", "Book id is invalid");
                List<Book> books = bookRepository.findByLibraryOwner(user);
                model.addAttribute("books", books.stream().filter(x -> x.getHolderId() != null).collect(Collectors.toList()));
                return "libraryOperationsPage";
            }

            return "redirect:/errorPage";

        } catch (Exception e) {
            return "redirect:/errorPage";
        }
    }


    @RequestMapping("/clear/{id}")
    public String clearBookById(@AuthenticationPrincipal User user, @PathVariable String id) {
        try {
            Optional<Book> book = bookRepository.findById(Long.parseLong(id));
            if (book.isPresent() && book.get().getLibraryOwner().getUsername().equals(user.getUsername()) && book.get().getDateWhenTaken() == null) {
                Book realBook = book.get();
                realBook.setHolderId(null);
                realBook.setDateWhenTaken(null);
                bookRepository.save(realBook);

                return "redirect:/library/operations";
            }

            return "redirect:/errorPage";

        } catch (Exception e) {
            return "redirect:/errorPage";
        }
    }
}
