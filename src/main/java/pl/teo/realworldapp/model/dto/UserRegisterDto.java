package pl.teo.realworldapp.model.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@JsonRootName("user")
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
}
