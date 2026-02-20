package pl.teo.realworldapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.teo.realworldapp.app.exception.ApiForbiddenException;
import pl.teo.realworldapp.app.exception.ApiNotFoundException;
import pl.teo.realworldapp.model.dto.CommentCreateDto;
import pl.teo.realworldapp.model.dto.CommentViewDto;
import pl.teo.realworldapp.model.entity.Article;
import pl.teo.realworldapp.model.entity.Comment;
import pl.teo.realworldapp.model.repositories.ArticleRepo;
import pl.teo.realworldapp.model.repositories.CommentRepository;
import pl.teo.realworldapp.service.CommentService;
import pl.teo.realworldapp.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepo articleRepo;
    private final ModelMapper mapper;
    private final UserService userService;

    @Override
    @Transactional
    public CommentViewDto addComment(String slug, CommentCreateDto commentCreateDto) {
        Article article = articleRepo.getArticleBySlug(slug)
                .orElseThrow(() -> new ApiNotFoundException("Article doesn't exists!"));
        Comment comment = new Comment(commentCreateDto.getBody(), LocalDateTime.now(), LocalDateTime.now(), userService.getCurrentUser(), article);
        Comment saved = commentRepository.save(comment);
        article.getComments().add(saved);
        articleRepo.save(article);
        return mapper.map(saved, CommentViewDto.class);
    }

    @Override
    public List<CommentViewDto> getComments(String slug) {
        if (!articleRepo.existsBySlug(slug))
            throw new ApiNotFoundException("Article doesn't exists!");
        List<Comment> commentsBySlug = articleRepo.findCommentsBySlug(slug);
        return commentsBySlug.stream().map(c -> mapper.map(c, CommentViewDto.class)).toList();
    }

    @Override
    public void deleteComment(String slug, long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Comment doesn't exists!"));
        if (Objects.equals(comment.getAuthor().getId(), userService.getCurrentUser().getId())) {
            commentRepository.delete(comment);
        } else
            throw new ApiForbiddenException("You can delete only your comments");
    }

}
