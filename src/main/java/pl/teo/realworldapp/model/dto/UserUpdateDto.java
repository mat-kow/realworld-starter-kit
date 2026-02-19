package pl.teo.realworldapp.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@JsonRootName("user")
public class UserUpdateDto {
    private String username;
    private String email;
    private String password;
    private String bio;
    private String image;
}
