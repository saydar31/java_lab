package ru.itis.service.util;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface UploadFileManager {
    void saveToStorage(MultipartFile multipartFile,String newFileName);
    Path getPath(String currentFileName);
}
