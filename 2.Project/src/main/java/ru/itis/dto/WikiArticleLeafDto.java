package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.WikiArticle;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WikiArticleLeafDto {
    private Long id;
    private String name;

    public static WikiArticleLeafDto from(WikiArticle wikiArticle) {
        return WikiArticleLeafDto.builder()
                .id(wikiArticle.getId())
                .name(wikiArticle.getName())
                .build();
    }
}
