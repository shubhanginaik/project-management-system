package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.UserRepo;
import fs19.java.backend.domain.entity.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepo {

  private final Map<UUID, Users> inMemoryUserDatabase = new ConcurrentHashMap<>();

  @Override
  public void saveUser(Users user) {
    inMemoryUserDatabase.put(user.getId(), user);
  }

  @Override
  public List<Users> findAllUsers() {
    return new ArrayList<>(inMemoryUserDatabase.values());
  }

  @Override
  public void deleteUser(Users user) {
    inMemoryUserDatabase.remove(user.getId());
  }

  @Override
  public Optional<Users> findById(UUID id) {
    return Optional.ofNullable(inMemoryUserDatabase.get(id));
  }
}
