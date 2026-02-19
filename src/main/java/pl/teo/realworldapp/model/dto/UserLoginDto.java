package pl.teo.realworldapp.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@JsonRootName("user")
public class UserLoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
