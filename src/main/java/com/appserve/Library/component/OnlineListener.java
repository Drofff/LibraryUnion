package com.appserve.Library.component;

import com.appserve.Library.service.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class OnlineListener implements HttpSessionListener {

    @Autowired
    OnlineService onlineService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        if (isLoggedIn()) {
            onlineService.setOnline(SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }

    private boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if (isLoggedIn()) {
            onlineService.setOffline(SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }
}
