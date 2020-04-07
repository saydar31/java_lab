package ru.itis.service;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.WikiArticleDto;
import ru.itis.dto.WikiArticleLeafDto;
import ru.itis.dto.WikiFolderDto;
import ru.itis.model.User;
import ru.itis.model.WikiFolder;

public interface WikiService {
    WikiArticleDto getArticleById(Long id);

    WikiFolderDto getFolderById(Long id);

    WikiFolderDto getBaseFolder();

    WikiFolderDto createFolder(Long parentFolderId, String newFolderName);

    WikiArticleLeafDto createArticle(Long parentFolderId, String name,String content, User author);
    WikiArticleLeafDto createArticle(Long parentFolderId, String name,User author, MultipartFile multipartFile);
}
