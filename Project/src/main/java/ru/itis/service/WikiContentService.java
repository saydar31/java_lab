package ru.itis.service;

import ru.itis.model.WikiArticle;

public interface WikiContentService {
    String getHtmlContent(WikiArticle article);

    String getMdContent(WikiArticle article);

    void setNewContent(WikiArticle article,String newContent);
}
