package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;
import pl.teo.realworldapp.model.dto.CommentCreateDto;
import pl.teo.realworldapp.model.dto.CommentViewDto;

import java.util.List;
import java.util.Set;

public interface ArticleService {
    ArticleViewDto create(ArticleCreateDto articleCreateDto);
    ArticleViewDto getArticle(String slug);
    ArticleViewDto updateArticle(String slug, ArticleCreateDto articleCreateDto);
    void delete(String slug);

    List<ArticleViewDto> getAll(int limit, int offset);
    List<ArticleViewDto> getByTag(int limit, int offset, String tag);
    List<ArticleViewDto> getByAuthor(int limit, int offset, String authorName);
    List<ArticleViewDto> getByFollowedAuthors(int limit, int offset);
    List<ArticleViewDto> getByFavorited(int limit, int offset, String favoritedUserName);

    CommentViewDto addComment(String slug, CommentCreateDto commentCreateDto);
    List<CommentViewDto> getComments(String slug);
    void deleteComment(String slug, long id);

    ArticleViewDto addToFavorite(String slug);

    ArticleViewDto removeFromFavorite(String slug);

    Set<String> getTags();
}
