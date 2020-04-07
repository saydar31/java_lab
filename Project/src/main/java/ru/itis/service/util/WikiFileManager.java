package ru.itis.service.util;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.model.WikiArticle;
import ru.itis.model.WikiFolder;

import java.io.File;

public interface WikiFileManager {
    File getCurrentVersionFile(WikiArticle wikiArticle);

    void createArticle( WikiArticle article, MultipartFile multipartFile);
    void createArticle( WikiArticle article);

    void createFolder(WikiFolder child);
}
