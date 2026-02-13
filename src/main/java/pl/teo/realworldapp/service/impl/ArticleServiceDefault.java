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

    private String generateSlug(String title) {
        Slugify slg = Slugify.builder()
                .lowerCase(true)
                .build();
        return slg.slugify(title);
    }

}
