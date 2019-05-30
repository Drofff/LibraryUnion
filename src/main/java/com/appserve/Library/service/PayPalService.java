package com.appserve.Library.service;

import com.appserve.Library.entity.User;
import com.appserve.Library.repository.PaymentRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class PayPalService {

    @Autowired
    PaymentRepository paymentRepository;

    public static final String clientId = "AZA30WN98y8HxSLJ2NelFrHoXHIz5UfMug37ybdSobFq-vwAspCrl_VXAblvGJ3i44Rb7cxvrZeWi0KJ";

    public static final String clientSecret = "ELaT3TbgV_pXHcevG1hmAAmomyCQlTQsCHKgviYeI68GEcGxnpdnlUcOMkIVwUbf9ZOrA7HyqGuFx16x";

    public String extendSubscription() {

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("15");

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Buy primary subscription in Library Union System");

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setTransactions(Arrays.asList(transaction));
        payment.setIntent("sale");
        payment.setPayer(payer);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/primary");
        redirectUrls.setReturnUrl("http://localhost:8080/primary");

        payment.setRedirectUrls(redirectUrls);

        try {

            APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");
            Payment createdPayment = payment.create(apiContext);

            for (Links links : createdPayment.getLinks()) {

                if (links.getRel().equals("approval_url")) {
                    return links.getHref();
                }

            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean compleateExtension(String paymentId, String payerId, User user) {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        try {

            Payment createdPayment = payment.execute(new APIContext(clientId, clientSecret, "sandbox"), paymentExecution);

            if (createdPayment != null) {

                LocalDate deadline = LocalDate.now().plusMonths(6);

                com.appserve.Library.entity.Payment result = new com.appserve.Library.entity.Payment();
                result.setUser(user);
                result.setDeadline(deadline);

                paymentRepository.save(result);

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

}
