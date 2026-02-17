package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;

import java.security.Principal;
import java.util.List;

public interface ArticleService {
    ArticleViewDto create(ArticleCreateDto articleCreateDto, Principal principal);
    ArticleViewDto getArticle(String slug);
    List<ArticleViewDto> getAll(int limit, int offset);
    List<ArticleViewDto> getByTag(int limit, int offset, String tag);
    List<ArticleViewDto> getByAuthor(int limit, int offset, String authorName);
    List<ArticleViewDto> getByFollowedAuthors(int limit, int offset, Principal principal);
    List<ArticleViewDto> getByFavorited(int limit, int offset, String favoritedUserName);
}
