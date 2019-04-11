package com.appserve.Library.service;

import com.appserve.Library.entity.User;
import com.appserve.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


@Service
public class AccountActivationService {

    @Autowired
    private MailService mailService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserRepository userRepository;

    private Map<String, String> activationTokens = new HashMap<>();

    private Map<String, String> userPasswords = new HashMap<>();

    public void sendActivationMail(String username) {
        String token = UUID.randomUUID().toString();
        activationTokens.put(token, username);
        String nameOfReceiver = startFromBigL(username);
        mailService.sendMessage(username, "Activate your account", "Hello, " + nameOfReceiver + "\nPlease follow this link to activate your account\n\nhttp://localhost:8080/activate?token=" + token);
    }

    public String startFromBigL(String username) {
        String nameOfReceiver = username.split("@")[0];
        char firstL = nameOfReceiver.charAt(0);
        return ("" + firstL).toUpperCase() + nameOfReceiver.substring(1);
    }

    public boolean activateAccount(String token, HttpServletRequest request) {
        if (activationTokens.containsKey(token)) {
            User user = userRepository.findByUsername(activationTokens.get(token));
            user.setActive(true);
            userRepository.save(user);
            activationTokens.remove(token);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), retrievePassword(user.getUsername()));
            SecurityContext context = SecurityContextHolder.getContext();

            context.setAuthentication(authenticationProvider.authenticate(usernamePasswordAuthenticationToken));
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

            return true;
        }

        return false;
    }

    public void savePassword(String username, String password) {
        userPasswords.put(username, password);
    }

    public String retrievePassword(String username) {
        String passwd = userPasswords.get(username);
        userPasswords.remove(username);
        return passwd;
    }

}
