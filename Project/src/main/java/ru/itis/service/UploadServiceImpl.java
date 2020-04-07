package ru.itis.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UploadFileDto;
import ru.itis.dto.UploadingDto;
import ru.itis.model.UploadFile;
import ru.itis.model.User;
import ru.itis.repositories.UploadFileRepository;
import ru.itis.service.util.UploadFileManager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class UploadServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private UploadFileManager uploadFileManager;

    private Random random = new Random();

    private String randomAlphaNumeric() {
        String alphabet = "ABCDEFGHIJKMLNOPQRSTUVWYZabcdefghijklmnopqrstuvwyz0123456789";
        int alphabetLength = alphabet.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(alphabet.charAt(Math.abs(random.nextInt()) % alphabetLength));
        }
        return stringBuilder.toString();
    }

    @Override
    public UploadingDto doService(MultipartFile multipartFile, User user) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String newFilename = Long.toString(System.currentTimeMillis()) + System.nanoTime() + randomAlphaNumeric() + "." + extension;
        uploadFileManager.saveToStorage(multipartFile, newFilename);
        UploadFile uploadFile = UploadFile.builder()
                .originalName(multipartFile.getOriginalFilename())
                .user(user)
                .currentPath(newFilename)
                .build();
        uploadFileRepository.save(uploadFile);
        return new UploadingDto("ok", newFilename);
    }

    @Override
    public Path getRequestedFile(String hash, User user) {
        UploadFile uploadFile;
        Optional<UploadFile> uploadFileCandidate = uploadFileRepository.findByHash(hash);
        if (uploadFileCandidate.isPresent()) {
            uploadFile = uploadFileCandidate.get();
            if (!uploadFile.getUser().equals(user)) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return uploadFileManager.getPath(uploadFile.getCurrentPath());
    }

    @Override
    public List<UploadFileDto> getUsersFiles(User user) {
        List<UploadFileDto> result = new ArrayList<>();
        List<UploadFile> files = uploadFileRepository.findAllByUser(user);
        files.stream().map(UploadFileDto::from).forEach(result::add);
        return result;
    }
}
