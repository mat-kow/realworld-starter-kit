package pl.teo.realworldapp.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@JsonRootName("article")
@Getter
public class CommentCreateDto {
    private String body;
}
