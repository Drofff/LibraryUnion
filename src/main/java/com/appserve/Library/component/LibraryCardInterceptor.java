package com.appserve.Library.component;

import com.appserve.Library.entity.Library;
import com.appserve.Library.entity.LibraryCard;
import com.appserve.Library.entity.User;
import com.appserve.Library.repository.LibraryAccountRepository;
import com.appserve.Library.repository.LibraryRepository;
import com.appserve.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LibraryCardInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryAccountRepository accountRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getUserPrincipal() != null) {

            User user = userRepository.findByUsername(request.getUserPrincipal().getName());

            if (user != null) {

                LibraryCard card = libraryRepository.findByUserId(user);
                Library library = accountRepository.findByAccount(user);


                if (card == null && library == null) {
                    if (isProtected(request.getRequestURI())) {
                        response.sendRedirect("/makeLibraryCard");
                        return false;
                    }
                }

            }

        }

        return true;
    }

    public boolean isProtected(String url) {
        return !url.equals("/libraryCard") && !url.equals("/makeLibraryCard") && !url.equals("/libraryAccount");
    }
}
