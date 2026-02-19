package pl.teo.realworldapp.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.teo.realworldapp.model.dto.CommentCreateDto;
import pl.teo.realworldapp.model.dto.CommentViewDto;
import pl.teo.realworldapp.service.ArticleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles/{slug}/comments")
public class CommentsController {


    private final ArticleService articleService;

    public CommentsController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@PathVariable String slug, @RequestBody @Valid CommentCreateDto commentCreateDto) {
        CommentViewDto commentViewDto = articleService.addComment(slug, commentCreateDto);
        return ResponseEntity.status(201).body(getCommentMapWrapper(commentViewDto));
    }

    @GetMapping
    public Map<String, Object> getComments(@PathVariable String slug) {
        List<CommentViewDto> list = articleService.getComments(slug);
        return Map.of("comments", list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable String slug, @PathVariable long id) {
        articleService.deleteComment(slug, id);
        return ResponseEntity.status(204).build();
    }

    private Map<String, Object> getCommentMapWrapper(Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("comment", object);
        return map;
    }

}
