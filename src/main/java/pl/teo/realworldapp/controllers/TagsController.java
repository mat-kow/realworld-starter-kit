package pl.teo.realworldapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.teo.realworldapp.service.ArticleService;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagsController {

    private final ArticleService articleService;

    @GetMapping()
    public ResponseEntity<Map<String, Set<String>>> getTags() {
        Set<String> tags = articleService.getTags();
        return ResponseEntity.ok(Map.of("tags", tags));
    }
}
