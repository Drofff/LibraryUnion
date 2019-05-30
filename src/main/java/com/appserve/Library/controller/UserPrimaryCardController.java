package com.appserve.Library.controller;

import com.appserve.Library.entity.LibraryCard;
import com.appserve.Library.entity.Payment;
import com.appserve.Library.entity.Subscription;
import com.appserve.Library.entity.User;
import com.appserve.Library.repository.LibraryRepository;
import com.appserve.Library.repository.PaymentRepository;
import com.appserve.Library.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/primary")
public class UserPrimaryCardController {

    @Autowired
    PayPalService payPalService;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @RequestMapping
    public String primaryCardGetPage(HttpServletRequest request, Model model, @AuthenticationPrincipal User user) {

        String payerId = request.getParameter("PayerID");
        String paymentId = request.getParameter("paymentId");

        if (paymentId != null && payerId != null) {

           if (payPalService.compleateExtension(paymentId, payerId, user)) {

               Set<Subscription> subscriptionSet = new HashSet<>();
               subscriptionSet.add(Subscription.REGULAR);
               subscriptionSet.add(Subscription.PRIMARY);

               LibraryCard card = libraryRepository.findByUserId(user);
               card.setCardSubscriptions(subscriptionSet);

               libraryRepository.save(card);

               model.addAttribute("successMessage", "Successfully extended! Thank you!");

           } else {

               model.addAttribute("errorMessage", "Sorry, something went wrong..");

           }
        }

        List<Payment> paymentList = paymentRepository.findByUser(user);

        for (Payment payment : paymentList) {
            if (payment.getDeadline().isAfter(LocalDate.now())) {
                model.addAttribute("valid", payment.getDeadline());
                break;
            }
        }

        return "getPrimaryCardPage";
    }

    @RequestMapping("/pay")
    public String payForSubscription(HttpServletResponse response, Model model) {

        String redirectUrl = payPalService.extendSubscription();

        if (redirectUrl == null) {
            model.addAttribute("message", "Sorry, something wrong..");
            return "getPrimaryCardPage";
        }

        try {

            response.sendRedirect(redirectUrl);

        } catch (Exception e) {

            return "errorPage";

        }

        return "getPrimaryCardPage";
    }

}
