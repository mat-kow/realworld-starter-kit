package pl.teo.realworldapp.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
@JsonRootName("user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(unique = true, nullable = false)
    private String username;

//    @Column(unique = true, nullable = false)
    private String email;

//    @Column(nullable = false)
    private String password;

    private String bio;
    private String image;



    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
