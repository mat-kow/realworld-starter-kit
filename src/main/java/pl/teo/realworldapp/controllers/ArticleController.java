package pl.teo.realworldapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;
import pl.teo.realworldapp.service.ArticleService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createArticle(@RequestBody @Valid ArticleCreateDto articleCreateDto) {
        ArticleViewDto articleViewDto = articleService.create(articleCreateDto);
        return ResponseEntity.status(201).body(getArticleMapWrapper(articleViewDto));
    }

    @GetMapping("{slug}")
    public ResponseEntity<Map<String, Object>> getArticle(@PathVariable String slug) {
        ArticleViewDto articleViewDto = articleService.getArticle(slug);
        return ResponseEntity.ok(getArticleMapWrapper(articleViewDto));
    }

    @PutMapping("{slug}")
    public ResponseEntity<Map<String, Object>> updateArticle(@PathVariable String slug, @RequestBody ArticleCreateDto articleCreateDto) {
        ArticleViewDto articleViewDto = articleService.updateArticle(slug, articleCreateDto);
        return ResponseEntity.ok(getArticleMapWrapper(articleViewDto));
    }

    @DeleteMapping("{slug}")
    public ResponseEntity<Object> deleteArticle(@PathVariable String slug) {
        articleService.delete(slug);
        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listArticles(@RequestParam(required = false) String tag,
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
        return ResponseEntity.ok(Map.of("articles", list));
    }

    @GetMapping("/feed")
    public ResponseEntity<Map<String, Object>> feed(@RequestParam(defaultValue = "20") int limit,
                                    @RequestParam(defaultValue = "0") int offset) {
        List<ArticleViewDto> list = articleService.getByFollowedAuthors(limit, offset);
        return ResponseEntity.ok(Map.of("articles", list));
    }

    @PostMapping("/{slug}/favorite")
    public ResponseEntity<Map<String, Object>> favArticle(@PathVariable String slug) {
        ArticleViewDto articleViewDto = articleService.addToFavorite(slug);
        return ResponseEntity.ok(getArticleMapWrapper(articleViewDto));
    }

    @DeleteMapping("/{slug}/favorite")
    public ResponseEntity<Map<String, Object>> unFavArticle(@PathVariable String slug) {
        ArticleViewDto articleViewDto = articleService.removeFromFavorite(slug);
        return ResponseEntity.ok(getArticleMapWrapper(articleViewDto));
    }

    private Map<String, Object> getArticleMapWrapper(Object object) {
        return Map.of("article", object);
    }
}
