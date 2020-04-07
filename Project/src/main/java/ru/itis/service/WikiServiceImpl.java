package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.WikiArticleDto;
import ru.itis.dto.WikiArticleLeafDto;
import ru.itis.dto.WikiFolderDto;
import ru.itis.model.User;
import ru.itis.model.WikiArticle;
import ru.itis.model.WikiArticleVersion;
import ru.itis.model.WikiFolder;
import ru.itis.repositories.WikiArticleRepository;
import ru.itis.repositories.WikiFolderRepository;
import ru.itis.service.util.WikiFileManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class WikiServiceImpl implements WikiService {
    @Autowired
    private WikiArticleRepository wikiArticleRepository;

    @Autowired
    private WikiFolderRepository wikiFolderRepository;
    @Autowired
    private WikiContentService wikiContentService;
    @Autowired
    private WikiFileManager wikiFileManager;

    @Override
    public WikiArticleDto getArticleById(Long id) {
        Optional<WikiArticle> wikiArticleOptional = wikiArticleRepository.find(id);
        if (wikiArticleOptional.isPresent()) {
            WikiArticle wikiArticle = wikiArticleOptional.get();
            String content = wikiContentService.getHtmlContent(wikiArticle);
            return WikiArticleDto.builder()
                    .htmlContent(content)
                    .name(wikiArticle.getName())
                    .build();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public WikiFolderDto getFolderById(Long id) {
        Optional<WikiFolder> wikiFolderOptional = wikiFolderRepository.find(id);
        if (wikiFolderOptional.isPresent()) {
            return WikiFolderDto.from(wikiFolderOptional.get());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public WikiFolderDto getBaseFolder() {
        return getFolderById(1L);
    }

    @Override
    public WikiFolderDto createFolder(Long parentFolderId, String newFolderName) {
        Optional<WikiFolder> wikiFolderOptional = wikiFolderRepository.find(parentFolderId);
        if (wikiFolderOptional.isPresent()) {
            WikiFolder parent = wikiFolderOptional.get();
            WikiFolder newChild = WikiFolder.builder()
                    .name(newFolderName)
                    .parentFolder(parent)
                    .childFolders(Set.of())
                    .articles(Set.of())
                    .build();
            parent.getChildFolders().add(newChild);
            wikiFolderRepository.update(parent);
            wikiFileManager.createFolder(newChild);
            return WikiFolderDto.from(newChild);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public WikiArticleLeafDto createArticle(Long parentFolderId, String name, String content, User author) {
        Optional<WikiFolder> folderOptional = wikiFolderRepository.find(parentFolderId);
        if (folderOptional.isPresent()){
            WikiFolder wikiFolder = folderOptional.get();
            WikiArticleVersion version = WikiArticleVersion.builder()
                    .creationDate(LocalDateTime.now())
                    .fileName("0.md")
                    .version("0")
                    .build();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public WikiArticleLeafDto createArticle(Long parentFolderId, String name, User author, MultipartFile multipartFile) {
        Optional<WikiFolder> folderOptional = wikiFolderRepository.find(parentFolderId);
        if (folderOptional.isPresent()) {
            WikiFolder wikiFolder = folderOptional.get();
            WikiArticle article = WikiArticle.builder()
                    .author(author)
                    .folder(wikiFolder)
                    .name(name)
                    .build();
            WikiArticleVersion version = WikiArticleVersion.builder()
                    .version("0")
                    .fileName("0.md")
                    .creationDate(LocalDateTime.now())
                    .build();
            article.setCurrentVersion(version);
            article.setVersions(List.of(version));
            wikiFolder.getArticles().add(article);
            wikiFolderRepository.update(wikiFolder);
            wikiFileManager.createArticle(article, multipartFile);
            return WikiArticleLeafDto.from(article);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
