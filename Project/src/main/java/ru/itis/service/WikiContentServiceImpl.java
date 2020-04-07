package ru.itis.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.model.WikiArticle;
import ru.itis.service.util.WikiFileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        File mdFile = fileManager.getCurrentVersionFile(article);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(mdFile));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = bufferedReader.readLine();
            }
            Node node = parser.parse(stringBuilder.toString());
            return renderer.render(node);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
