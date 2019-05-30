package com.appserve.Library.controller;

import com.appserve.Library.entity.*;
import com.appserve.Library.repository.*;
import com.appserve.Library.service.AccountActivationService;
import com.appserve.Library.service.EntityCreationService;
import com.appserve.Library.service.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    OnlineService onlineService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountActivationService accountActivationService;

    @Autowired
    BookHistoryRepository bookHistoryRepository;

    @Autowired
    LibraryAccountRepository libraryAccountRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    EntityCreationService entityCreationService;

    @Autowired
    BlockedIpRepository blockedIpRepository;

    @RequestMapping
    public String getAdminPage(Model model, @AuthenticationPrincipal User user, HttpServletRequest request){

        model.addAttribute("address", request.getRemoteAddr());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("usersOnline", onlineService.countOnlineUsers());
        model.addAttribute("libInSys", libraryAccountRepository.findAll().size());
        model.addAttribute("apps", libraryAccountRepository.findAll().stream().filter(x -> !x.isActivated()).count());
        model.addAttribute("admins", onlineService.getAdmins());

        return "adminMainPage";
    }

    @RequestMapping("/applications")
    public String getLibrariesApplications(Model model) {
        model.addAttribute("apps", accountActivationService.getApplications());
        return "applicationsPage";
    }

    @RequestMapping("/applications/accept/{id}")
    public String acceptApplication(@PathVariable String id, Model model) {
        try {

            Long libId = Long.parseLong(id);

            Library library = libraryAccountRepository.findById(libId).get();
            library.setActivated(true);

            libraryAccountRepository.save(library);

            model.addAttribute("message", "Application successfully accepted");
            model.addAttribute("apps", accountActivationService.getApplications());
            return "applicationsPage";

        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage";
        }
    }

    @RequestMapping("/applications/decline/{id}")
    public String declineApplication(@PathVariable String id, Model model) {
        try {
            Long idOfLib = Long.parseLong(id);
            libraryAccountRepository.deleteById(idOfLib);
            model.addAttribute("message", "Deleted successfully");
            model.addAttribute("apps", accountActivationService.getApplications());
            return "applicationsPage";
        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage";
        }
    }

    @RequestMapping("/blockIp")
    public String blockIpPage(Model model) {
        model.addAttribute("ips", blockedIpRepository.findAll());
        model.addAttribute("apps", accountActivationService.getApplications());
        return "blockIpPage";
    }

    @PostMapping("/blockIp")
    public String blockIpPatePostMethod(@RequestParam String ip, @RequestParam String reason, Model model) {

        if (entityCreationService.isIpAddress(ip)) {

            if (reason == null || reason.isEmpty()) {
                model.addAttribute("message", "Reason field can't be empty");
            } else {
                BlockedIp blockedIp = new BlockedIp();
                blockedIp.setIp(ip);
                blockedIp.setReason(reason);
                blockedIpRepository.save(blockedIp);
                model.addAttribute("message", ip + " was successfully blocked");
            }

        } else {
            model.addAttribute("message", "Wrong ip address");
        }

        model.addAttribute("ips", blockedIpRepository.findAll());
        model.addAttribute("apps", accountActivationService.getApplications());

        return "blockIpPage";
    }

    @RequestMapping("/blockIp/unblock/{id}")
    public String unblockIpMethod(Model model, @PathVariable String id) {
        try {

            Long ipId = Long.parseLong(id);
            blockedIpRepository.deleteById(ipId);

            model.addAttribute("message", "IP Address unblocked");
            model.addAttribute("ips", blockedIpRepository.findAll());
            model.addAttribute("apps", accountActivationService.getApplications());

            return "blockIpPage";
        } catch (Exception e) {
            return "errorPage";
        }
    }

    @RequestMapping("/usrList")
    public String getUserList(Model model, HttpServletRequest request) {

        String query = request.getParameter("search");

        List<User> users = userRepository.findAll();

        List<LibraryCard> cards = libraryRepository.findAll();

        if (query != null && !query.isEmpty()) {
            users = users.stream().filter(x -> x.getUsername().toLowerCase().matches(".*(" + query.toLowerCase() + ").*")).collect(Collectors.toList());
        }

        Map<User, Boolean> userWithLibraryCards = new LinkedHashMap<>();

        for (User u : users) {
            if (cards.stream().filter(x -> x.getUserId().getUsername().equals(u.getUsername())).count() > 0) {
                userWithLibraryCards.put(u, true);
            } else {
                userWithLibraryCards.put(u, false);
            }
        }

        model.addAttribute("query", query);
        model.addAttribute("users", userWithLibraryCards);
        model.addAttribute("apps", accountActivationService.getApplications().size());
        return "userListPage";
    }

    @RequestMapping("/statistic")
    public String getUserStatistic(@RequestParam Long user, Model model) {

        Optional<User> userOpt = userRepository.findById(user);

        if (userOpt.isPresent()) {

            LibraryCard libraryCard = libraryRepository.findByUserId(userOpt.get());

            if (libraryCard != null) {

                model.addAttribute("currentBooks", libraryCard.getBooksTaken().size());
                model.addAttribute("userId", userOpt.get().getId());
                model.addAttribute("username", userOpt.get().getUsername());
                model.addAttribute("cardType", libraryCard.getCardSubscriptions().contains(Subscription.PRIMARY));
                model.addAttribute("apps", accountActivationService.getApplications().size());

                List<BookHistory> history = bookHistoryRepository.findByHolderId(userOpt.get());

                if (history != null && history.size() > 0) {

                    LinkedList<Integer> booksStatistic = new LinkedList<>();

                    Map<LocalDate, Integer> failedBooks = new LinkedHashMap<>();

                    for (BookHistory bookHistory : history) {

                        LocalDate endDate = LocalDate.parse(bookHistory.getDateOfEnd());
                        LocalDate deadline = LocalDate.parse(bookHistory.getDateOfStart()).plusWeeks(1);

                        int status = 0;

                        if ( endDate.isAfter(deadline) ) {
                            status++;
                        }

                        if (failedBooks.containsKey(endDate)) {
                            failedBooks.put(endDate, failedBooks.get(endDate) + status);
                        } else {
                            failedBooks.put(endDate, status);
                        }

                    }

                    Iterator<Map.Entry<LocalDate, Integer>> iterator = failedBooks.entrySet().iterator();

                    Integer sum = 0;

                    while (iterator.hasNext()) {
                        Integer current = iterator.next().getValue();
                        booksStatistic.add(current);
                        sum += current;
                    }

                    model.addAttribute("booksStatistic", booksStatistic);
                    model.addAttribute("booksLate", sum);
                    model.addAttribute("maxBooksStatistic", booksStatistic.stream().max((x, y) -> x - y).get());

                }

                return "userStatisticPage";

            }

        }

        return "redirect:/admin/usrList";

    }

    @RequestMapping("/block/user/{userId}")
    public String blockUser(@PathVariable String userId, HttpServletRequest request) {
        try {

            User user = userRepository.findById(Long.parseLong(userId)).get();

            if (user.isAdmin()) {
                throw new Exception("Can not delete admin account");
            }

            userRepository.delete(user);

            String referer = request.getHeader("referer");

            if (referer != null) return "redirect:" + referer;

            return "redirect:/admin";

        } catch (Exception e) {
           return "redirect:/errorPage";
        }
    }

    @RequestMapping("/libList")
    public String getListOfLibraries(Model model) {
        List<Library> libraries = libraryAccountRepository.findAll();
        Map<Library, Integer> librariesAndBooks = new LinkedHashMap<>();

        for (Library l : libraries) {
            librariesAndBooks.put(l, bookRepository.findByLibraryOwner(l.getAccount()).size());
        }

        model.addAttribute("libs", librariesAndBooks);
        model.addAttribute("apps", accountActivationService.getApplications().size());
        return "librariesListPage";
    }


}
