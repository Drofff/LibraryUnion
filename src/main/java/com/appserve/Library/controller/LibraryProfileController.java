package com.appserve.Library.controller;

import com.appserve.Library.entity.Library;
import com.appserve.Library.entity.User;
import com.appserve.Library.repository.BookRepository;
import com.appserve.Library.repository.LibraryAccountRepository;
import com.appserve.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/library")
@PreAuthorize("hasAuthority('LIBRARY')")
public class LibraryProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryAccountRepository libraryAccountRepository;

    @RequestMapping("/profile")
    public String libraryProfilePage(Model model, @AuthenticationPrincipal User user) {
        Library library = libraryAccountRepository.findByAccount(user);
        library.setProvidedBooks(bookRepository.findByLibraryOwner(user));
        model.addAttribute("lib", library);
        return "libraryProfilePage";
    }

    @RequestMapping("/passwd")
    public String changePasswordPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("email", user.getUsername());
        return "changePasswordPage";
    }

    @PostMapping("/passwd")
    public String changePasswordDo(Model model, @AuthenticationPrincipal User user, @RequestParam(name = "old-passwd") String oldPasswd, @RequestParam String passwd, @RequestParam(name = "repeat-passwd") String repeatPasswd) {
        model.addAttribute("email", user.getUsername());
        if (new BCryptPasswordEncoder().matches(oldPasswd, user.getPassword())) {
            if (passwd == null || passwd.isEmpty()) {
                model.addAttribute("message", "Password field is empty");
                return "changePasswordPage";
            }
            if (passwd.equals(repeatPasswd)) {
                user.setPassword(new BCryptPasswordEncoder().encode(passwd));
                userRepository.save(user);
                model.addAttribute("message", "Password successfully changed");
                model.addAttribute("lib", libraryAccountRepository.findByAccount(user));
                return "libraryProfilePage";
            } else {
                model.addAttribute("message", "Password and repeated password are different");
                return "changePasswordPage";
            }
        } else {
            model.addAttribute("message", "Old password is invalid");
            return "changePasswordPage";
        }
    }

    @RequestMapping("/data")
    public String dataChangePage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("lib", libraryAccountRepository.findByAccount(user));
        return "libraryProfileDataChangePage";
    }

    @PostMapping("/data")
    public String changeDataAboutLibrary(Model model, @AuthenticationPrincipal User user, @RequestParam String photoUrl, @RequestParam String phoneNumber, @RequestParam String name, @RequestParam String address) {
        Map<String, String> errors = validateData("photoUrl%" + photoUrl, "phoneNumber%" + phoneNumber, "name%" + name, "address%" + address);
        Library library = libraryAccountRepository.findByAccount(user);
        if (errors.size() == 0) {
            library.setAddress(address);
            library.setName(name);
            library.setPhotoUrl(photoUrl);
            library.setPhoneNumber(phoneNumber);
            libraryAccountRepository.save(library);
            model.addAttribute("message", "<i class='fas fa-check'></i> Saved");
            model.addAttribute("lib", libraryAccountRepository.findByAccount(user));
            return "libraryProfilePage";
        } else {
            model.addAttribute("lib", library);
            model.mergeAttributes(errors);
            return "libraryProfileDataChangePage";
        }
    }

    public Map<String, String> validateData(String ... args) {
        Map<String, String> errors = new HashMap<>();
        for (String s : args) {
            String [] str = s.split("%");
            String nameOfField = str[0];
            String dataOfField = str[1];
            if (dataOfField == null || dataOfField.isEmpty() || dataOfField.length() < 3) {
                errors.put(nameOfField + "Error", nameOfField + " must be at least 4 chars");
            }
        }
        return errors;
    }


}
