package pl.teo.realworldapp.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentViewDto {
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Profile author;

}
