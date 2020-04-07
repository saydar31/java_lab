package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.WikiFolder;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WikiFolderDto {
    private Long id;
    private String name;
    private Long parentId;
    private Set<WikiFolderDto> children;
    private Set<WikiArticleLeafDto> leafs;

    public static WikiFolderDto from(WikiFolder wikiFolder) {
        return WikiFolderDto.builder()
                .parentId(wikiFolder.getParentFolder() != null ? wikiFolder.getParentFolder().getId() : null)
                .children(wikiFolder.getChildFolders() != null ? wikiFolder.getChildFolders().stream().map(WikiFolderDto::from).collect(Collectors.toSet()) : null)
                .leafs(wikiFolder.getArticles() != null ? wikiFolder.getArticles().stream().map(WikiArticleLeafDto::from).collect(Collectors.toSet()) : null)
                .name(wikiFolder.getName())
                .id(wikiFolder.getId())
                .build();

    }
}
