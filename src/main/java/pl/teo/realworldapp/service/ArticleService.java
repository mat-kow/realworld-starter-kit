package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;

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

    ArticleViewDto addToFavorite(String slug);
    ArticleViewDto removeFromFavorite(String slug);
    Set<String> getTags();
}
