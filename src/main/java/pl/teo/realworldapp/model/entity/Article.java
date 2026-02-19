package pl.teo.realworldapp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "articles")
@Getter @Setter
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> taglist;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int favoritesCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}
