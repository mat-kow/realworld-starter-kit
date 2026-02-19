package pl.teo.realworldapp.model.entity;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
@JsonRootName("user")
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String bio;
    private String image;
    @ManyToMany
    private List<User> followed;
    @ManyToMany
    private List<Article> favArticles;



    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
