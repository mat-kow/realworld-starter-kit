package pl.teo.realworldapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;
import pl.teo.realworldapp.service.ArticleService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("")
    public Map<String, Object> createArticle(@RequestBody ArticleCreateDto articleCreateDto, Principal principal) {
        ArticleViewDto articleViewDto = articleService.create(articleCreateDto, principal);
        return getArticleMapWrapper(articleViewDto);
    }

    @GetMapping("{slug}")
    public Map<String, Object> getArticle(@PathVariable String slug) {
        ArticleViewDto articleViewDto = articleService.getArticle(slug);
        return getArticleMapWrapper(articleViewDto);
    }

//    @PutMapping("{slug}")
//    public Map<String, Object> updateArticle(@PathVariable String slug) {
//        ArticleViewDto articleViewDto = articleService.getArticle(slug);
//        return getArticleMapWrapper(articleViewDto);
//    }

    @GetMapping
    public Map<String, Object> listArticles(@RequestParam(required = false) String tag,
                                            @RequestParam(required = false) String author,
                                            @RequestParam(required = false) String favorited,
                                            @RequestParam(defaultValue = "20") int limit,
                                            @RequestParam(defaultValue = "0") int offset
                                            ) {
        List<ArticleViewDto> list;
        if (tag != null) {
            list = articleService.getByTag(limit, offset, tag);
        } else if (author != null) {
            list = articleService.getByAuthor(limit, offset, author);
        } else if (favorited != null) {
            list = articleService.getByAuthor(limit, offset, favorited);
        } else {
            list = articleService.getAll(limit, offset);
        }
        return Map.of("articles", list);
    }

    private Map<String, Object> getArticleMapWrapper(Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("article", object);
        return map;
    }
}
