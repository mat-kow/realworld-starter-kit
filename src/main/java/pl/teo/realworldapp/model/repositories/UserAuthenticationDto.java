package pl.teo.realworldapp.model.repositories;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName("user")
public class UserAuthenticationDto {
    private String username;
    private String email;
    private String token;
    private String bio;
    private String image;

}
