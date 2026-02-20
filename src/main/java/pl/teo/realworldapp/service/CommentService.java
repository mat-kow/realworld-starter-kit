package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.dto.CommentCreateDto;
import pl.teo.realworldapp.model.dto.CommentViewDto;

import java.util.List;

public interface CommentService {
    CommentViewDto addComment(String slug, CommentCreateDto commentCreateDto);
    List<CommentViewDto> getComments(String slug);
    void deleteComment(String slug, long id);

}
