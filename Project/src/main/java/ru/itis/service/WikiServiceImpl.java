package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.WikiArticleDto;
import ru.itis.dto.WikiArticleLeafDto;
import ru.itis.dto.WikiArticleMdDto;
import ru.itis.dto.WikiFolderDto;
import ru.itis.model.User;
import ru.itis.model.WikiArticle;
import ru.itis.model.WikiArticleVersion;
import ru.itis.model.WikiFolder;
import ru.itis.repositories.WikiArticleRepository;
import ru.itis.repositories.WikiArticleVersionRepository;
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

    @Autowired
    private WikiArticleVersionRepository versionRepository;

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

    public WikiArticleMdDto getMdArticleById(Long id) {
        Optional<WikiArticle> wikiArticleOptional = wikiArticleRepository.find(id);
        if (wikiArticleOptional.isPresent()) {
            WikiArticle wikiArticle = wikiArticleOptional.get();
            String mdArticleContent = wikiContentService.getMdContent(wikiArticle);
            return WikiArticleMdDto.builder()
                    .id(wikiArticle.getId())
                    .name(wikiArticle.getName())
                    .mdContent(mdArticleContent)
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
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
        Optional<WikiFolder> wikiFolderOptional = wikiFolderRepository.find(1L);
        if (wikiFolderOptional.isPresent()) {
            return WikiFolderDto.from(wikiFolderOptional.get());
        } else {
            WikiFolder wikiFolder = WikiFolder.builder()
                    .name("BaseFolder")
                    .build();
            wikiFolderRepository.save(wikiFolder);
            wikiFileManager.createFolder(wikiFolder);
            return WikiFolderDto.from(wikiFolder);
        }
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
            wikiFolderRepository.save(newChild);
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
        if (folderOptional.isPresent()) {
            WikiFolder wikiFolder = folderOptional.get();
            WikiArticleVersion version = WikiArticleVersion.builder()
                    .creationDate(LocalDateTime.now())
                    .fileName("0.md")
                    .version("0")
                    .build();
            WikiArticle article = WikiArticle.builder()
                    .author(author)
                    .currentVersion(version)
                    .folder(wikiFolder)
                    .name(name)
                    .versions(List.of(version))
                    .build();
            wikiArticleRepository.save(article);
            wikiFolder.getArticles().add(article);
            wikiFileManager.createArticle(article, content);
            wikiFolderRepository.update(wikiFolder);
            return WikiArticleLeafDto.from(article);
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
            wikiArticleRepository.save(article);
            wikiFolder.getArticles().add(article);
            wikiFolderRepository.update(wikiFolder);
            wikiFileManager.createArticle(article, multipartFile);
            return WikiArticleLeafDto.from(article);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void updateArticle(Long articleId, String newContent, String version) {
        Optional<WikiArticle> wikiArticleOptional = wikiArticleRepository.find(articleId);
        if (wikiArticleOptional.isPresent()) {
            WikiArticle wikiArticle = wikiArticleOptional.get();
            WikiArticleVersion articleVersion = WikiArticleVersion.builder()
                    .creationDate(LocalDateTime.now())
                    .fileName(version + ".md")
                    .version(version)
                    .build();
            versionRepository.save(articleVersion);
            wikiArticle.setCurrentVersion(articleVersion);
            wikiArticle.getVersions().add(articleVersion);
            wikiArticleRepository.update(wikiArticle);
            wikiContentService.setNewContent(wikiArticle, newContent);
        }
    }
}
