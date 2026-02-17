package pl.teo.realworldapp.service.impl;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.teo.realworldapp.model.Article;
import pl.teo.realworldapp.model.User;
import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;
import pl.teo.realworldapp.model.repositories.ArticleRepo;
import pl.teo.realworldapp.service.ArticleService;
import pl.teo.realworldapp.service.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceDefault implements ArticleService {

    private final ArticleRepo articleRepo;
    private final ModelMapper mapper;
    private final UserService userService;

    @Override
    public ArticleViewDto create(ArticleCreateDto articleCreateDto, Principal principal) {
        Article article = mapper.map(articleCreateDto, Article.class);
        LocalDateTime now = LocalDateTime.now();
        article.setCreatedAt(now);
        article.setUpdatedAt(now);
        article.setSlug(generateSlug(article.getTitle()));
        User user = userService.getCurrentUser(principal);
        article.setAuthor(user);
        Article saved = articleRepo.save(article);
        return mapper.map(saved, ArticleViewDto.class);
    }

    @Override
    public ArticleViewDto getArticle(String slug) {
        //todo 404
        Article article = articleRepo.getArticleBySlug(slug).orElseThrow(() -> new RuntimeException("Article not found"));
        return mapper.map(article, ArticleViewDto.class);
    }

    @Override
    public List<ArticleViewDto> getAll(int limit, int offset) {
        return articleRepo.getAll(limit, offset).stream()
                .map(a -> mapper.map(a, ArticleViewDto.class))
                .toList();
    }

    @Override
    public List<ArticleViewDto> getByTag(int limit, int offset, String tag) {
        return articleRepo.getArticlesByTag(tag, limit, offset).stream()
                .map(a -> mapper.map(a, ArticleViewDto.class))
                .toList();
    }

    @Override
    public List<ArticleViewDto> getByAuthor(int limit, int offset, String authorName) {
        return articleRepo.getArticlesByAuthorName(authorName, limit, offset).stream()
                .map(a -> mapper.map(a, ArticleViewDto.class))
                .toList();
    }

    @Override
    public List<ArticleViewDto> getByFollowedAuthors(int limit, int offset, Principal principal) {
        List<User> followed = userService.getCurrentUser(principal).getFollowed();
        return followed.stream()
                .map(User::getUsername)
                .map(authorName -> articleRepo.getArticlesByTag(authorName, limit, offset))
                .flatMap(Collection::stream)
                .map(a -> mapper.map(a, ArticleViewDto.class))
                .toList();
    }

    @Override
    public List<ArticleViewDto> getByFavorited(int limit, int offset, String favoritedUserName) {
        return articleRepo.getArticlesByAuthorName(favoritedUserName, limit, offset).stream()
                .map(a -> mapper.map(a, ArticleViewDto.class))
                .toList();
    }

    private String generateSlug(String title) {
        Slugify slg = Slugify.builder()
                .lowerCase(true)
                .build();
        return slg.slugify(title);
    }

}
