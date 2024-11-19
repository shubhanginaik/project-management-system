package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.CommentRepository;
import fs19.java.backend.domain.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CommentRepoImpl implements CommentRepository {

    private final Map<UUID, Comment> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public void save(Comment comment) {
        inMemoryDatabase.put(comment.getId(), comment);

    }

    @Override
    public Optional<Comment> findById(UUID id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public List<Comment> findAll() {
       return new ArrayList<>(inMemoryDatabase.values());
    }

    @Override
    public void deleteById(UUID id) {
        inMemoryDatabase.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return inMemoryDatabase.containsKey(id);
    }
}
