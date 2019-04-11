package com.appserve.Library.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSenderImpl javaMailSender;


    public JavaMailSenderImpl getJavaMailSender() {

        if (javaMailSender == null) {

            javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setHost("smtp.mailtrap.io");
            javaMailSender.setPort(465);
            javaMailSender.setUsername("8b57e990161d0a");
            javaMailSender.setPassword("e0c6c44eeead3e");
        }
        return javaMailSender;
    }

    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("LibraryUnion@corp.com");
        mailMessage.setText(text);
        mailMessage.setSubject(subject);
        mailMessage.setTo(to);
        getJavaMailSender().send(mailMessage);
    }

}
