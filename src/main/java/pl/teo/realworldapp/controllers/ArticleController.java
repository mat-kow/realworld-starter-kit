package pl.teo.realworldapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;
import pl.teo.realworldapp.service.ArticleService;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<ArticleViewDto> createArticle(@RequestBody ArticleCreateDto articleCreateDto, Principal principal) {
        ArticleViewDto articleViewDto = articleService.create(articleCreateDto, principal);
        return ResponseEntity.ok(articleViewDto);
    }
}
