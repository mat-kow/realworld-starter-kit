package pl.teo.realworldapp.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.teo.realworldapp.model.entity.Article;
import pl.teo.realworldapp.model.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface ArticleRepo extends CrudRepository<Article, Long> {
    Optional<Article> getArticleBySlug(String slug);

    @Query(
            value = "SELECT * FROM articles " +
                    "ORDER BY CREATED_AT DESC LIMIT ?1 OFFSET ?2",
            nativeQuery = true
    )
    List<Article> getAll(int limit, int offset);

    @Query(
            value = "SELECT * FROM articles " +
                    "WHERE ARRAY_CONTAINS(TAGLIST, ?1)" +
                    "ORDER BY CREATED_AT DESC LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    List<Article> getArticlesByTag(String tag, int limit, int offset);

    @Query(
            value = "SELECT * FROM articles " +
                    "WHERE ARTICLES.USER_ID = (SELECT USERS.ID FROM USERS WHERE USERNAME = ?1)" +
                    "ORDER BY CREATED_AT DESC LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    List<Article> getArticlesByAuthorName(String authorName, int limit, int offset);

    @Query(
            value = "SELECT * FROM articles " +
                    "WHERE ID IN (" +
                    "   SELECT USERS_FAV_ARTICLES.FAV_ARTICLES_ID FROM USERS INNER JOIN USERS_FAV_ARTICLES ON users.id = USERS_FAV_ARTICLES.USER_ID" +
                    "   WHERE username = ?1)" +
                    "ORDER BY CREATED_AT DESC LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    List<Article> getArticlesByFavorited(String userName, int limit, int offset);

    void deleteArticleBySlug(String slug);

    @Query("SELECT a.comments FROM Article a WHERE a.slug = ?1")
    List<Comment> findCommentsBySlug(String slug);

    @Query(nativeQuery = true,
            value = "SELECT ARTICLES.TAGLIST FROM ARTICLES"
    )
    List<List<String>> getAllTags();

    boolean existsBySlug(String slug);
}
