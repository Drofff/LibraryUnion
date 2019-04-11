package com.appserve.Library.controller;

import com.appserve.Library.entity.LibraryCard;
import com.appserve.Library.entity.Role;
import com.appserve.Library.entity.Subscription;
import com.appserve.Library.entity.User;
import com.appserve.Library.repository.LibraryRepository;
import com.appserve.Library.repository.UserRepository;
import com.appserve.Library.service.AccountActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private AccountActivationService accountActivationService;

    private int count = 0;

    @RequestMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {

        String queryString = request.getQueryString();

        if ( queryString != null && queryString.equals("error")) {
            model.addAttribute("message", "Invalid username or password");

            count++;

            if (count == 3) {
                count = 0;
                model.addAttribute("forgot", true);
            }

        }

        return "login";
    }

    @RequestMapping("/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping("/registration")
    public String registrationPage() {
        return "regist";
    }

    @PostMapping("/registration")
    public String register(@Valid User user, BindingResult bindingResult, @RequestParam String confirm, Model model) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "User with this username already exists");
            return "regist";
        }

        boolean confirmed = user.getPassword().equals(confirm);

        if ( (bindingResult.getFieldErrors() == null || bindingResult.getFieldErrors().size() == 0) && confirmed) {

            accountActivationService.savePassword(user.getUsername(), user.getPassword());

            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(Role.USER);

            user.setRoles(roleSet);
            user.setActive(false);

            userRepository.save(user);

            accountActivationService.sendActivationMail(user.getUsername());

            model.addAttribute("mail", user.getUsername());

            return "activationPage";

        } else {
            model.addAttribute("oldEmail", user.getUsername());
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().stream().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
            model.mergeAttributes(errors);

            if (confirmed == false) {
                model.addAttribute("confirmationError", true);
            }

            System.out.println(errors.entrySet());

            return "regist";
        }

    }

    @RequestMapping("/activate")
    public String activateUserAccount(@RequestParam String token, Model model, HttpServletRequest request) {
        if (accountActivationService.activateAccount(token, request)) {
            return "makeLibraryCardPage";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/makeLibraryCard")
    public String makeLibraryCardProposePage() {
        return "makeLibraryCardPage";
    }

    @RequestMapping("/libraryCard")
    public String createLibraryCardPage(@AuthenticationPrincipal User user) {
        return "makeLibraryCard";
    }

    @PostMapping("/libraryCard")
    public String createLibraryCard(@Valid LibraryCard libraryCard, BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.getFieldErrors() == null || bindingResult.getFieldErrorCount() == 0) {

            Set<Subscription> subscriptions = new HashSet<>();
            subscriptions.add(Subscription.REGULAR);

            libraryCard.setCardSubscriptions(subscriptions);
            libraryCard.setUserId(user);

            libraryRepository.save(libraryCard);

            return "redirect:/";
        } else {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
            model.mergeAttributes(errors);

            model.addAttribute("oldFirstName", libraryCard.getFirstName());
            model.addAttribute("oldLastName", libraryCard.getLastName());
            model.addAttribute("oldAddress", libraryCard.getAddress());
            model.addAttribute("oldPhotoUrl", libraryCard.getPhotoUrl());
            return "makeLibraryCard";
        }
    }

    //TODO Working with flex utiliti for main menu
    //TODO pass forgot + remember me + horiz card as book

}
