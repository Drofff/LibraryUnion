package com.appserve.Library.service;

import com.appserve.Library.entity.LibraryCard;
import com.appserve.Library.entity.Payment;
import com.appserve.Library.entity.Subscription;
import com.appserve.Library.entity.User;
import com.appserve.Library.repository.LibraryRepository;
import com.appserve.Library.repository.PaymentRepository;
import com.appserve.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    public boolean checkIfSubscriptionIsActive() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            User user = userRepository.findByUsername(auth.getName());
            return check(user);
        }

        return false;
    }

    public boolean check(User user) {
        LibraryCard card = libraryRepository.findByUserId(user);

        if (card != null && card.getCardSubscriptions().contains(Subscription.PRIMARY)) {

            for (Payment payment : paymentRepository.findByUser(user)) {

                if (payment.getDeadline().isAfter(LocalDate.now())) {
                    return true;
                }

            }

            card.getCardSubscriptions().remove(Subscription.PRIMARY);
            libraryRepository.save(card);

        }

        return false;
    }

}