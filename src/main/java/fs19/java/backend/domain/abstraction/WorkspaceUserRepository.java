package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.WorkspaceUser;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceUserRepository {
  void save(WorkspaceUser workspaceUsers);
  Optional<WorkspaceUser> findById(UUID id);
  List<WorkspaceUser> findAll();
  void deleteById(UUID id);
  boolean existsById(UUID id);
}
