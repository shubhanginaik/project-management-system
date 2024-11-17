package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.ProjectRepository;
import fs19.java.backend.domain.entity.Project;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
  private final Map<UUID, Project> inMemoryUserDatabase = new ConcurrentHashMap<>();

  @Override
  public void saveProject(Project project) {
    inMemoryUserDatabase.put(project.getId(), project);
  }

  @Override
  public List<Project> findAllProjects() {
    return new ArrayList<>(inMemoryUserDatabase.values());
  }

  @Override
  public void deleteProject(Project project) {
    inMemoryUserDatabase.remove(project.getId());
  }

  @Override
  public Optional<Project> findById(UUID id) {
    return Optional.ofNullable(inMemoryUserDatabase.get(id));
  }
}
