package pl.teo.realworldapp.service;

import pl.teo.realworldapp.model.dto.ArticleCreateDto;
import pl.teo.realworldapp.model.dto.ArticleViewDto;

import java.security.Principal;

public interface ArticleService {
    ArticleViewDto create(ArticleCreateDto articleCreateDto, Principal principal);
}
