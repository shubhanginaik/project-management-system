package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.WorkspaceUserRepository;
import fs19.java.backend.domain.entity.WorkspaceUser;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WorkspaceUserRepoImpl implements WorkspaceUserRepository {
  private final Map<UUID, WorkspaceUser> inMemoryWorkspaceUsersDatabase = new ConcurrentHashMap<>();

  @Override
  public void save(WorkspaceUser workspaceUsers) {
    inMemoryWorkspaceUsersDatabase.put(workspaceUsers.getId(), workspaceUsers);
  }

  @Override
  public Optional<WorkspaceUser> findById(UUID id) {
    return Optional.ofNullable(inMemoryWorkspaceUsersDatabase.get(id));
  }

  @Override
  public List<WorkspaceUser> findAll() {
    return new ArrayList<>(inMemoryWorkspaceUsersDatabase.values());
  }

  @Override
  public void deleteById(UUID id) {
    inMemoryWorkspaceUsersDatabase.remove(id);
  }

  @Override
  public boolean existsById(UUID id) {
    return inMemoryWorkspaceUsersDatabase.containsKey(id);
  }
}
