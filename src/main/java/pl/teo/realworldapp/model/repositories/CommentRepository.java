package pl.teo.realworldapp.model.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.teo.realworldapp.model.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
