package ru.itis.service.util;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.model.WikiArticle;
import ru.itis.model.WikiFolder;

public interface WikiFileManager {
    String getCurrentVersionFileContent(WikiArticle wikiArticle);

    void createArticle(WikiArticle article, MultipartFile multipartFile);

    void createArticle(WikiArticle article, String content);

    void createFolder(WikiFolder child);

    void setNewVersionContent(WikiArticle article, String newContent);
}
