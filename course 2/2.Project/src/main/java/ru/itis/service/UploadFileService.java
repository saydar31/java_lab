package ru.itis.service;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UploadFileDto;
import ru.itis.dto.UploadingDto;
import ru.itis.dto.UserDto;
import ru.itis.model.User;

import java.nio.file.Path;
import java.util.List;

public interface UploadFileService {
    UploadingDto doService(MultipartFile multipartFile, User user);

    Path getRequestedFile(String hash, User user);

    List<UploadFileDto> getUsersFiles(User user);
}
