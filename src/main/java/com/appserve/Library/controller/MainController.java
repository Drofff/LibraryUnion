package com.appserve.Library.controller;

import com.appserve.Library.entity.*;
import com.appserve.Library.repository.BookRepository;
import com.appserve.Library.repository.LibraryAccountRepository;
import com.appserve.Library.repository.LibraryRepository;
import com.appserve.Library.repository.UserRepository;
import com.appserve.Library.service.AccountActivationService;
import com.appserve.Library.service.EntityCreationService;
import com.appserve.Library.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Controller
@MultipartConfig(maxFileSize = 1024 * 1024 * 30, maxRequestSize =  1024 * 1024 * 30)
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private LibraryAccountRepository libraryAccountRepository;

    @Autowired
    private EntityCreationService creationService;

    @Autowired
    private AccountActivationService accountActivationService;

    @Autowired
    private MailService mailService;

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
    public String mainPage(@AuthenticationPrincipal User user, Model model) {

        if (user != null && user.isLibrary()) {

            return "redirect:/library";

        } else if (user != null) {

            LibraryCard libraryCard = libraryRepository.findByUserId(user);
            model.addAttribute("photoUrl", libraryCard.getPhotoUrl());

            if (user.isAdmin()){
                model.addAttribute("admin", "yes");
            }

            Map<Book, Library> books = new LinkedHashMap<>();
            for (Book b : bookRepository.findAll()) {
                books.put(b, libraryAccountRepository.findByAccount(b.getLibraryOwner()));
            }

            model.addAttribute("books", books);

            List<Map<String, Object>> booksRented = new LinkedList<>();

            for (Book b : bookRepository.findByHolderId(libraryCard)) {

                Map<String, Object> data = new HashMap<>();
                String text = "";

                data.put("missed", false);
                if (b.getDateWhenTaken() == null) {
                    text = b.getName() + " <span class='badge badge-primary'>reserved</span>";
                } else {
                    LocalDate deadline = b.getDateWhenTaken().plusWeeks(1);
                    if (LocalDate.now().isAfter(deadline)) {
                        data.put("missed", true);
                    }
                    text = b.getName() + " <span class='badge badge-danger'>" + deadline + "</span>";
                }
                data.put("text", text);
                data.put("id", b.getId());

                booksRented.add(data);

            }

            model.addAttribute("myBooks", booksRented);

            return "mainPage";
        }

        model.addAttribute("libCount", libraryAccountRepository.count());
        model.addAttribute("bookCount", bookRepository.count());

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

            try {
                accountActivationService.sendActivationMail(user.getUsername());
            } catch ( Exception e ) {
                e.printStackTrace();
                return "errorPage";
            }

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
    public String makeLibraryCardProposePage(@AuthenticationPrincipal User user) {
        LibraryCard card = libraryRepository.findByUserId(user);
        Library library = libraryAccountRepository.findByAccount(user);

        if ( card == null && library == null) {
            return "makeLibraryCardPage";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/libraryCard")
    public String createLibraryCardPage(@AuthenticationPrincipal User user) {
        LibraryCard card = libraryRepository.findByUserId(user);
        Library library = libraryAccountRepository.findByAccount(user);

        if ( card == null && library == null ) {
            return "makeLibraryCard";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/libraryAccount")
    public String createLibraryAccountAction(@AuthenticationPrincipal User user, @RequestParam MultipartFile documentUrl, @Valid Library library, BindingResult result, Model model) {

        boolean isFileValid = documentUrl != null && !documentUrl.isEmpty();

        if ( (result.getFieldErrors() == null || result.getFieldErrors().size() < 2 ) && isFileValid) {

            if (creationService.createLibraryAccount(library, user, documentUrl)) {
                return "libraryWait";
            } else {
                model.addAttribute("message", "Error");
                return "createLibraryAccount";
            }

        } else {

            Map<String, String> errors = new HashMap<>();

            result.getFieldErrors().stream().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));

            if (!isFileValid) {
                model.addAttribute("documentUrl", "Sorry, but this file do not exists or empty");
            }

            model.mergeAttributes(errors);
            model.addAttribute("oldName", library.getName());
            model.addAttribute("oldDocument", documentUrl);
            model.addAttribute("oldAddress", library.getAddress());
            model.addAttribute("oldPhoneNumber", library.getPhoneNumber());
            model.addAttribute("oldPhotoUrl", library.getPhotoUrl());

            return "createLibraryAccount";

        }

    }


    @RequestMapping("/libraryAccount")
    public String createLibraryAccountPage(@AuthenticationPrincipal User user) {
        LibraryCard card = libraryRepository.findByUserId(user);
        Library library = libraryAccountRepository.findByAccount(user);

        if (card != null || library != null) {
            return "redirect:/";
        }

        return "createLibraryAccount";
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

    @RequestMapping("/recover")
    public String recoveryGetPage() {
        return "askForEmailForForgot";
    }

    @PostMapping("/recover")
    public String getRecoverPage(@RequestParam String email, Model model) {

        String token = UUID.randomUUID().toString();

        User user = userRepository.findByUsername(email);

        if (user == null) {

            model.addAttribute("message", "Wrong email");
            return "askForEmailForForgot";

        } else {

            user.setPassword(new BCryptPasswordEncoder().encode(token));

            mailService.sendMessage(user.getUsername(), "Password recovery", "Hello, User! Your new password is:\n\n\n\t" + token + "\n\nAfter login you can change it in profile settings\nThank you!");

            userRepository.save(user);

            model.addAttribute("email", user.getUsername());

            return "passwordRecoverPage";

        }
    }

    @RequestMapping("/errorPage")
    public String errorPage() {
        return "errorPage";
    }

    @RequestMapping("/blockedPage")
    public String blockedPage() {
        return "youAreBlocked";
    }

}
