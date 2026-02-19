package pl.teo.realworldapp.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("comment")
@Getter @Setter
public class CommentCreateDto {
    @NotNull @NotBlank
    private String body;
}
