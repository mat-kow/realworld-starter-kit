package pl.teo.realworldapp.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonRootName("article")
@Getter @Setter
public class ArticleViewDto {
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> taglist;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int favoritesCount;
    private Profile author;

}
