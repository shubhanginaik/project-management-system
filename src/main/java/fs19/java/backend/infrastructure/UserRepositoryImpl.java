package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.UserRepository;
import fs19.java.backend.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final Map<UUID, User> inMemoryUserDatabase = new ConcurrentHashMap<>();

  @Override
  public void saveUser(User user) {
    inMemoryUserDatabase.put(user.getId(), user);
  }

  @Override
  public List<User> findAllUsers() {
    return new ArrayList<>(inMemoryUserDatabase.values());
  }

  @Override
  public void deleteUser(User user) {
    inMemoryUserDatabase.remove(user.getId());
  }

  @Override
  public Optional<User> findById(UUID id) {
    return Optional.ofNullable(inMemoryUserDatabase.get(id));
  }
}
