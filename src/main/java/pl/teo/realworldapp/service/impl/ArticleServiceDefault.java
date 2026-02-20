package pl.teo.realworldapp.service.impl;

import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.teo.realworldapp.app.exception.ApiForbiddenException;
import pl.teo.realworldapp.app.exception.ApiNonUniqueValueException;
import pl.teo.realworldapp.app.exception.ApiNotFoundException;
import pl.teo.realworldapp.app.exception.ApiUnAuthorizedException;
import pl.teo.realworldapp.model.entity.Article;
import pl.teo.realworldapp.model.entity.User;
import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;
import pl.teo.realworldapp.model.repositories.ArticleRepo;
import pl.teo.realworldapp.model.repositories.CommentRepository;
import pl.teo.realworldapp.service.ArticleService;
import pl.teo.realworldapp.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceDefault implements ArticleService {

    private final ArticleRepo articleRepo;
    private final ModelMapper mapper;
    private final UserService userService;

    @Override
    public ArticleViewDto create(ArticleCreateDto articleCreateDto) {
        if (articleRepo.existsByTitleIgnoreCase(articleCreateDto.getTitle()))
            throw new ApiNonUniqueValueException("Article with that title already exists");
        Article article = mapper.map(articleCreateDto, Article.class);
        LocalDateTime now = LocalDateTime.now();
        article.setCreatedAt(now);
        article.setUpdatedAt(now);
        article.setSlug(generateSlug(article.getTitle()));
        User user = userService.getCurrentUser();
        article.setAuthor(user);
        Article saved = articleRepo.save(article);
        return mapper.map(saved, ArticleViewDto.class);
    }

    @Override
    public ArticleViewDto getArticle(String slug) {
        Article article = getArticleBySlug(slug);
        return mapper.map(article, ArticleViewDto.class);
    }

    @Override
    public ArticleViewDto updateArticle(String slug, ArticleCreateDto articleCreateDto) {
        Article article = getArticleBySlug(slug);
        if (!article.getAuthor().getId().equals(userService.getCurrentUser().getId()))
            throw new ApiForbiddenException("You can update only your articles");

        if (articleCreateDto.getTitle() != null) {
            article.setTitle(articleCreateDto.getTitle());
            article.setSlug(generateSlug(article.getTitle()));
        }
        if (articleCreateDto.getDescription() != null)  article.setDescription(articleCreateDto.getDescription());
        if (articleCreateDto.getBody() != null)  article.setBody(articleCreateDto.getBody());
        article.setUpdatedAt(LocalDateTime.now());
        Article saved = articleRepo.save(article);
        return mapper.map(saved, ArticleViewDto.class);
    }

    @Override
    @Transactional
    public void delete(String slug) {
        Article article = articleRepo.getArticleBySlug(slug)
                .orElseThrow(() -> new ApiNotFoundException("There is no article with that slug"));
        if (article.getAuthor().getId().equals(userService.getCurrentUser().getId()))
            articleRepo.deleteArticleBySlug(slug);
        else
            throw new ApiForbiddenException("You can delete only your articles");
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
    public List<ArticleViewDto> getByFollowedAuthors(int limit, int offset) {
        User currentUser;
        try {
            currentUser = userService.getCurrentUser();
        } catch (ApiNotFoundException e) {
            throw new ApiUnAuthorizedException("You need to be logged in");
        }
        List<User> followed = currentUser.getFollowed();
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


    @Override
    public ArticleViewDto addToFavorite(String slug) {
        Article article = getArticleBySlug(slug);
        User currentUser = userService.getCurrentUser();
        currentUser.getFavArticles().add(article);
        userService.update(currentUser);
        return mapper.map(article, ArticleViewDto.class);
    }

    @Override
    public ArticleViewDto removeFromFavorite(String slug) {
        Article article = getArticleBySlug(slug);
        User currentUser = userService.getCurrentUser();
        currentUser.getFavArticles().remove(article);
        userService.update(currentUser);
        return mapper.map(article, ArticleViewDto.class);
    }

    @Override
    public Set<String> getTags() {
        return articleRepo.getAllTags().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private String generateSlug(String title) {
        Slugify slg = Slugify.builder()
                .lowerCase(true)
                .build();
        return slg.slugify(title) + randomAlphaNum(6);
    }

    private static String randomAlphaNum(int size) {
        char[] result = new char[size];
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            result[i] = chars.charAt(random.nextInt(chars.length()));
        }
        return String.copyValueOf(result);
    }

    private Article getArticleBySlug(String slug) {
        return articleRepo.getArticleBySlug(slug)
                .orElseThrow(() -> new ApiNotFoundException("Article doesn't exists!"));
    }

}
