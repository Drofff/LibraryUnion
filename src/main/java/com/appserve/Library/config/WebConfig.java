package com.appserve.Library.config;

import com.appserve.Library.component.IpBlockInterceptor;
import com.appserve.Library.component.LibraryCardInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    LibraryCardInterceptor libraryCardInterceptor;

    @Autowired
    IpBlockInterceptor ipBlockInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(libraryCardInterceptor);
        registry.addInterceptor(ipBlockInterceptor);
    }
}
