package com.appserve.Library.controller;

import com.appserve.Library.entity.*;
import com.appserve.Library.repository.BookRepository;
import com.appserve.Library.repository.LibraryAccountRepository;
import com.appserve.Library.repository.LibraryRepository;
import com.appserve.Library.repository.UserRepository;
import com.appserve.Library.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserPageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryAccountRepository libraryAccountRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    SubscriptionService subscriptionService;

    @RequestMapping("/profile")
    public String userProfilePage(Model model, @AuthenticationPrincipal User user) {
        LibraryCard libraryCard = libraryRepository.findByUserId(user);
        libraryCard.setBooksTaken(bookRepository.findByHolderId(libraryCard));
        model.addAttribute("card", libraryCard);
        model.addAttribute("subscription", libraryCard.getCardSubscriptions().contains(Subscription.PRIMARY)? Subscription.PRIMARY : Subscription.REGULAR);
        return "userProfilePage";
    }

    @RequestMapping("/book/{id}")
    public String showBookById(@PathVariable String id, HttpServletRequest request, Model model, @AuthenticationPrincipal User user) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(Long.parseLong(id));
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                model.addAttribute("book", book);
                model.addAttribute("reserved", book.getHolderId());
                model.addAttribute("photoUrl", libraryRepository.findByUserId(user).getPhotoUrl());
                if (libraryRepository.findByUserId(user).getCardSubscriptions().contains(Subscription.PRIMARY)) {
                    model.addAttribute("userPrimary", "TRUE");
                }
                return "bookShowPage";
            } else {
                return "redirect:" + request.getRequestURI();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage";
        }
    }

    @PostMapping("/book/{id}")
    public String orderBook(@PathVariable String id, @AuthenticationPrincipal User user, Model model) {

        subscriptionService.check(user);

        try {
            Optional<Book> bookOpt = bookRepository.findById(Long.parseLong(id));
            if (bookOpt.isPresent()) {
                LibraryCard libraryCard = libraryRepository.findByUserId(user);
                Book book = bookOpt.get();
                List<Book> books = bookRepository.findByHolderId(libraryCard);
                int booksCount = books.size();

                if (libraryCard != null) {

                    if (!libraryCard.getCardSubscriptions().contains(Subscription.PRIMARY) && booksCount >= 5) {
                        model.addAttribute("message", "Sorry, but you can't have more than 5 books at the same time");
                        return "bookAddResult";
                    }

                    if (book.getDateWhenTaken() != null) {
                        for (Book b : books) {
                            if (b.getDateWhenTaken().isAfter(b.getDateWhenTaken().plusWeeks(1))) {
                                model.addAttribute("message", "You can't reserve books if you have at least one delayed book. You could reserve new books after you return previous");
                                return "bookAddResult";
                            }
                        }
                    }

                    if (libraryCard.getCardSubscriptions().contains(book.getSubscriptionType()) && book.getHolderId() == null) {
                        book.setHolderId(libraryCard);
                        bookRepository.save(book);
                        model.addAttribute("success", "true");
                        return "bookAddResult";
                    } else {
                        return "redirect:/book/" + book.getId();
                    }
                }
            }
                return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage";
        }
    }

    @RequestMapping("/owner/{id}")
    public String getLibraryInfo(@PathVariable String id, HttpServletRequest request, Model model) {

        try {
            Optional<Library> library = libraryAccountRepository.findById(Long.parseLong(id));
            if (library.isPresent()) {
                Library lib = library.get();
                lib.setProvidedBooks(bookRepository.findByLibraryOwner(lib.getAccount()));
                model.addAttribute("library", lib);
                return "ownerOfBookPage";
            } else {
                return "redirect:" + request.getRequestURI();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage";
        }
    }

    @RequestMapping("/changeProfile")
    public String changeProfileData(@AuthenticationPrincipal User user, Model model) {
        LibraryCard libraryCard = libraryRepository.findByUserId(user);

        if (libraryCard == null) {
            return "errorPage";
        }

        model.mergeAttributes(setLibraryCardToModel(libraryCard));
        model.addAttribute("user", user);

        return "changeProfileData";
    }

    public Map<String, Object> setLibraryCardToModel(LibraryCard libraryCard) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("oldFirstName", libraryCard.getFirstName());
        fields.put("oldLastName", libraryCard.getLastName());
        fields.put("oldDateOfBirthday", libraryCard.getDateOfBirthday());
        fields.put("oldAddress", libraryCard.getAddress());
        fields.put("oldPhotoUrl", libraryCard.getPhotoUrl());
        return fields;
    }

    @PostMapping("/changeProfile")
    public String changeProfileDataMethod(@AuthenticationPrincipal User user, @Valid LibraryCard libraryCard, BindingResult bindingResult, Model model) {

        if (bindingResult.getFieldErrors() == null || bindingResult.getFieldErrors().size() == 0) {
            LibraryCard card = libraryRepository.findByUserId(user);
            card.setFirstName(libraryCard.getFirstName());
            card.setLastName(libraryCard.getLastName());
            card.setDateOfBirthday(libraryCard.getDateOfBirthday());
            card.setAddress(libraryCard.getAddress());
            card.setPhotoUrl(libraryCard.getPhotoUrl());

            libraryRepository.save(card);

            return "redirect:/profile";
        } else {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().stream().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
            model.mergeAttributes(errors);
            model.mergeAttributes(setLibraryCardToModel(libraryCard));
            return "changeProfileData";
        }

    }

    @RequestMapping("/passwd")
    public String changeUserPassword(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("email", user.getUsername());
        return "changeUserPassword";
    }

    @PostMapping("/passwd")
    public String changePassword(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "old-passwd") String oldPasswd, @RequestParam String passwd, @RequestParam(name = "repeat-passwd") String repeatPasswd) {
        model.addAttribute("email", user.getUsername());
        if (new BCryptPasswordEncoder().matches(oldPasswd, user.getPassword())) {
            if (passwd == null || passwd.isEmpty()) {
                model.addAttribute("message", "Password field is empty");
                return "changeUserPassword";
            }
            if (passwd.equals(repeatPasswd)) {
                user.setPassword(new BCryptPasswordEncoder().encode(passwd));
                userRepository.save(user);
                model.addAttribute("messageSuccess", "Password successfully changed");
                return "changeUserPassword";
            } else {
                model.addAttribute("message", "Password and repeated password are different");
                return "changeUserPassword";
            }
        } else {
            model.addAttribute("message", "Old password is invalid");
            return "changeUserPassword";
        }
    }

}
