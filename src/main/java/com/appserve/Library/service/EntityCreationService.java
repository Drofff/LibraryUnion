package com.appserve.Library.service;

import com.appserve.Library.entity.Book;
import com.appserve.Library.entity.Library;
import com.appserve.Library.entity.Role;
import com.appserve.Library.entity.User;
import com.appserve.Library.repository.LibraryAccountRepository;
import com.appserve.Library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class EntityCreationService {

    @Autowired
    LibraryAccountRepository libraryAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StorageService storageService;

    public boolean createLibraryAccount(Library library, User user, MultipartFile document) {
        library.setAccount(user);
        library.setActivated(false);
        library.setProvidedBooks(new ArrayList<Book>());

        String filePath = storageService.saveFile(document);

        if (filePath == null) {
            return false;
        }

        library.setDocumentUrl(filePath);
        libraryAccountRepository.save(library);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.LIBRARY);
        roleSet.add(Role.USER);

        user.setRoles(roleSet);
        userRepository.save(user);

        return true;
    }

    public boolean isIpAddress(String ip) {
        return ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    }


}
