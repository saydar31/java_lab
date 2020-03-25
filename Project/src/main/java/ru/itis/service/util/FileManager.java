package ru.itis.service.util;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileManager {
    void saveToStorage(MultipartFile multipartFile,String newFileName);
    Path getPath(String currentFileName);
}
