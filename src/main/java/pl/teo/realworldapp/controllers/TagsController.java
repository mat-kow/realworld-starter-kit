package pl.teo.realworldapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.teo.realworldapp.service.ArticleService;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    private final ArticleService articleService;

    public TagsController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public Map<String, Set<String>> getTags() {
        Set<String> tags = articleService.getTags();
        return Map.of("tags", tags);
    }
}
