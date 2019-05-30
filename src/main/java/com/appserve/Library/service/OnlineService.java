package com.appserve.Library.service;

import com.appserve.Library.entity.User;
import com.appserve.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OnlineService {

    @Autowired
    UserRepository userRepository;

    private static HashMap<String, Boolean> onlineUsers = new HashMap<>();

    public void setOnline(String username) {
        onlineUsers.put(username, true);
    }

    public void setOffline(String username) {
        onlineUsers.put(username, false);
    }

    public Integer countOnlineUsers() {
        return onlineUsers.size();
    }

    public Map<String, Boolean> getAdmins() {
        List<User> admins = userRepository.findAll().stream().filter(x -> x.isAdmin()).collect(Collectors.toList());
        Map<String, Boolean> adminsData = new HashMap<>();

        for (User user : admins) {
            adminsData.put(user.getUsername(), onlineUsers.get(user.getUsername()) == null ? false : onlineUsers.get(user.getUsername()));
        }

        return adminsData;
    }

}
