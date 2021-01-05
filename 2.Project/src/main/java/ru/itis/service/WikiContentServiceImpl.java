package ru.itis.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.model.WikiArticle;
import ru.itis.service.util.WikiFileManager;

@Component
public class WikiContentServiceImpl implements WikiContentService {
    @Autowired
    private WikiFileManager fileManager;

    @Autowired
    private Parser parser;

    @Autowired
    private Renderer renderer;

    @Override
    public String getHtmlContent(WikiArticle article) {
        String mdContent = fileManager.getCurrentVersionFileContent(article);
        Node node = parser.parse(mdContent);
        return renderer.render(node);
    }

    public String getMdContent(WikiArticle article) {
        return fileManager.getCurrentVersionFileContent(article);
    }

    @Override
    public void setNewContent(WikiArticle article, String newContent) {
        fileManager.setNewVersionContent(article,newContent);
    }
}
