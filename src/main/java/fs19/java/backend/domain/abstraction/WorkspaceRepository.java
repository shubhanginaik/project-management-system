package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.Workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository {
    void save(Workspace workspace);
    Optional<Workspace> findById(UUID id);
    List<Workspace> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}