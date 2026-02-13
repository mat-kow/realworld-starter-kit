package pl.teo.realworldapp.model.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.teo.realworldapp.model.Article;

public interface ArticleRepo extends CrudRepository<Article, Long> {
}
