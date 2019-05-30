package com.appserve.Library.component;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FactoryCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        ErrorPage errorPage = new ErrorPage(HttpStatus.NOT_FOUND, "/errorPage");
        ErrorPage forbidErrorPage = new ErrorPage(HttpStatus.FORBIDDEN, "/errorPage");
        factory.addErrorPages(errorPage, forbidErrorPage);
    }
}
