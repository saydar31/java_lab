package ru.itis.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManagerImpl implements FileManager {
    @Autowired
    private String uploadPath;

    @Override
    public void saveToStorage(MultipartFile multipartFile, String newFileName) {
        try {
            File file = new File(uploadPath + newFileName);
            file.createNewFile();
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Path getPath(String currentFileName) {
        return Paths.get(uploadPath, currentFileName);
    }
}
