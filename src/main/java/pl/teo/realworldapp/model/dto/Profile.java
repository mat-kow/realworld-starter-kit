package pl.teo.realworldapp.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("profile")
@Getter @Setter @NoArgsConstructor
public class Profile {

    private String username;
    private String bio;
    private String image;
    private boolean following;

}
