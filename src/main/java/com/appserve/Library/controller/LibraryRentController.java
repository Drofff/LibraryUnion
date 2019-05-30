package com.appserve.Library.controller;

import com.appserve.Library.entity.*;
import com.appserve.Library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('LIBRARY')")
@RequestMapping("/library")
public class LibraryRentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibraryAccountRepository libraryAccountRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookHistoryRepository bookHistoryRepository;

    @RequestMapping("/analyse")
    public String getMainPageOfRentingInfo(@AuthenticationPrincipal User user, Model model) {
        Library library = libraryAccountRepository.findByAccount(user);

        if (library == null) {
            return "redirect:/";
        }

        Map<Book, List<BookHistory>> dataHistory = new LinkedHashMap<>();

        for (Book book : bookRepository.findByLibraryOwner(user)) {
              dataHistory.put(book, bookHistoryRepository.findByBookId(book));
        }

        model.addAttribute("bookInfo", dataHistory);

        return "libraryRentingInfoPage";
    }

    @RequestMapping("/card/{id}")
    public String infoAboutLibraryCardPage(@PathVariable String id, Model model) {
        try {
            Optional<User> userOptional = userRepository.findById(Long.parseLong(id));
            if (userOptional.isPresent()) {
                LibraryCard libraryCard = libraryRepository.findByUserId(userOptional.get());
                if (libraryCard != null) {
                    model.addAttribute("firstName", libraryCard.getFirstName());
                    model.addAttribute("lastName", libraryCard.getLastName());
                    model.addAttribute("dateOfBirthday", libraryCard.getDateOfBirthday());
                    model.addAttribute("address", libraryCard.getAddress());
                    model.addAttribute("email", userOptional.get().getUsername());
                    model.addAttribute("photoUrl", libraryCard.getPhotoUrl());
                    return "showLibraryCard";
                }
            }
            return "redirect:/errorPage";
        } catch (Exception e) {
            return "redirect:/errorPage";
        }
    }
}
