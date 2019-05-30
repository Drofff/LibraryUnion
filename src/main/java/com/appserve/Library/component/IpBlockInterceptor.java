package com.appserve.Library.component;

import com.appserve.Library.repository.BlockedIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IpBlockInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    BlockedIpRepository blockedIpRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();

        if (blockedIpRepository.findByIp(ip) != null && !request.getRequestURI().equals("/blockedPage")) {
            response.sendRedirect("/blockedPage");
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
