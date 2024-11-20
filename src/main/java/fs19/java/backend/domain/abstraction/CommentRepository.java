package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
    void save(Comment comment);
    Optional<Comment> findById(UUID id);
    List<Comment> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
