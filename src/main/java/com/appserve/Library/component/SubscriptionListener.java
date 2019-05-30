package com.appserve.Library.component;

import com.appserve.Library.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionListener implements ApplicationListener<HttpSessionCreatedEvent> {

    @Autowired
    SubscriptionService subscriptionService;

    @Override
    public void onApplicationEvent(HttpSessionCreatedEvent httpSessionCreatedEvent) {
        subscriptionService.checkIfSubscriptionIsActive();
    }
}
