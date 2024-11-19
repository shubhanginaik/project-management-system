package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.WorkspaceRepository;
import fs19.java.backend.domain.entity.Workspace;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WorkspaceRepoImpl implements WorkspaceRepository {

    private final Map<UUID, Workspace> inMemoryDatabase = new ConcurrentHashMap<>();


    @Override
    public void save(Workspace workspace) {
        inMemoryDatabase.put(workspace.getId(), workspace);
    }

    @Override
    public Optional<Workspace> findById(UUID id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public List<Workspace> findAll() {
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