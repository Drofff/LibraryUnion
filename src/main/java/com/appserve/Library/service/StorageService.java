package com.appserve.Library.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;

@Service
public class StorageService {

    Path directoryForFiles;

    public StorageService() {
        directoryForFiles = Paths.get("./libraryDocs").toAbsolutePath().normalize();

        try {
            if (!Files.isDirectory(directoryForFiles)) {
                Files.delete(directoryForFiles);
                Files.createDirectory(directoryForFiles);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String saveFile(MultipartFile file) {
        try {

            Files.copy(file.getInputStream(), Paths.get(directoryForFiles.toString() + "/" + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
