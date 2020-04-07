package ru.itis.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.model.WikiArticle;
import ru.itis.model.WikiFolder;

import java.io.File;
import java.io.IOException;

@Component
public class WikiFileManagerImpl implements WikiFileManager {
    @Qualifier("wikiStoragePath")
    @Autowired
    private String baseFolderPath;

    private String getFolderName(WikiFolder wikiFolder) {
        if (wikiFolder.getParentFolder() == null) {
            return baseFolderPath + File.separator + wikiFolder.getName();
        } else {
            return getFolderName(wikiFolder.getParentFolder()) + File.separator + wikiFolder.getName();
        }
    }

    private String getFileName(WikiArticle wikiArticle) {
        return getFolderName(wikiArticle.getFolder()) + File.separator + wikiArticle.getName() + File.separator + wikiArticle.getCurrentVersion().getFileName();
    }

    @Override
    public File getCurrentVersionFile(WikiArticle wikiArticle) {
        return new File(getFileName(wikiArticle));
    }

    @Override
    public void createArticle(WikiArticle article, MultipartFile multipartFile) {
        String versionFolderName = getFolderName(article.getFolder()) + File.separator + article.getName() + File.separator;
        File articleDir = new File(versionFolderName);
        articleDir.mkdirs();
        File articleVersionFile = new File(versionFolderName + File.separator + article.getCurrentVersion().getFileName());
        try {
            articleVersionFile.createNewFile();
            multipartFile.transferTo(articleVersionFile);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void createArticle(WikiArticle article) {

    }

    @Override
    public void createFolder(WikiFolder child) {
        File file = new File(getFolderName(child));
        file.mkdirs();
    }
}
