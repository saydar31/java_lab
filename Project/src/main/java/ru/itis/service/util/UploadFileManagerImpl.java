package ru.itis.service.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadFileManagerImpl implements UploadFileManager {
    private final String uploadPath;

    public UploadFileManagerImpl(@Qualifier("storagePath") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void saveToStorage(MultipartFile multipartFile, String newFileName) {
        try {
            File file = new File(uploadPath +File.separator+ newFileName);
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
            //noinspection ResultOfMethodCallIgnored
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
