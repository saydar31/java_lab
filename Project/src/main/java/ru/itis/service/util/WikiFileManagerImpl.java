package ru.itis.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.model.WikiArticle;
import ru.itis.model.WikiFolder;

import java.io.*;
import java.nio.charset.StandardCharsets;

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

    private String getCurrentVersionFileName(WikiArticle wikiArticle) {
        return getFolderName(wikiArticle.getFolder()) + File.separator + wikiArticle.getName() + File.separator + wikiArticle.getCurrentVersion().getFileName();
    }

    @Override
    public String getCurrentVersionFileContent(WikiArticle wikiArticle) {
        try {
            File file = new File(getCurrentVersionFileName(wikiArticle));
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
            inputStream.close();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void createArticle(WikiArticle article, MultipartFile multipartFile) {
        File articleVersionFile = createCurrentVersionFile(article);
        try {
            multipartFile.transferTo(articleVersionFile);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    private File createCurrentVersionFile(WikiArticle article) {
        String versionFolderName = getFolderName(article.getFolder()) + File.separator + article.getName() + File.separator;
        File articleDir = new File(versionFolderName);
        articleDir.mkdirs();
        File file = new File(versionFolderName + File.separator + article.getCurrentVersion().getFileName());
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        return file;
    }

    @Override
    public void createArticle(WikiArticle article, String content) {
        File currentVersionFile = createCurrentVersionFile(article);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(currentVersionFile));
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void createFolder(WikiFolder child) {
        File file = new File(getFolderName(child));
        file.mkdirs();
    }

    @Override
    public void setNewVersionContent(WikiArticle article, String newContent) {
        String versionFolderName = getFolderName(article.getFolder()) + File.separator + article.getName() + File.separator;
        File file = new File(versionFolderName + File.separator + article.getCurrentVersion().getFileName());
        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
