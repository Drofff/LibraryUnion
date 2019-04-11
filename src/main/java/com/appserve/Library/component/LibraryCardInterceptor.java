package com.appserve.Library.component;

import com.appserve.Library.entity.LibraryCard;
import com.appserve.Library.entity.User;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName());

        if (user != null) {

            LibraryCard card = libraryRepository.findByUserId(user);

            if (card == null) {
                if (!request.getRequestURI().equals("/libraryCard") && !request.getRequestURI().equals("/makeLibraryCard")) {
                    response.sendRedirect("/makeLibraryCard");
                    return false;
                }
            }
        }

        return true;
    }
}
