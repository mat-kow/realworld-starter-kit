package pl.teo.realworldapp.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonRootName("article")
@Getter @Setter
public class ArticleCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String body;
    private List<String> tagList;
}
