package pl.teo.realworldapp.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentViewDto {
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Profile author;

}
