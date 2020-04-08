package ru.itis.service;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.WikiArticleDto;
import ru.itis.dto.WikiArticleLeafDto;
import ru.itis.dto.WikiArticleMdDto;
import ru.itis.dto.WikiFolderDto;
import ru.itis.model.User;

public interface WikiService {
    WikiArticleDto getArticleById(Long id);

    WikiArticleMdDto getMdArticleById(Long id);

    WikiFolderDto getFolderById(Long id);

    WikiFolderDto getBaseFolder();

    WikiFolderDto createFolder(Long parentFolderId, String newFolderName);

    WikiArticleLeafDto createArticle(Long parentFolderId, String name, String content, User author);

    WikiArticleLeafDto createArticle(Long parentFolderId, String name, User author, MultipartFile multipartFile);

    void updateArticle(Long articleId, String neContent,String version);
}
