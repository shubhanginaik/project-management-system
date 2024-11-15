package fs19.java.backend.infrastructure.tempMemory;

import fs19.java.backend.domain.abstraction.UserRepo;
import fs19.java.backend.domain.entity.Users;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUserDatabase {

  private static final Map<UUID, Users> users = new ConcurrentHashMap<>();

  public Users saveUser(Users user) {
    users.put(user.getId(), user);
    return user;
  }

  public List<Users> findAllUsers() {
    return new ArrayList<>(users.values());
  }

  public Optional<Users> findById(UUID id) {
    return Optional.ofNullable(users.get(id));
  }

  public void deleteUser(Users user) {
    users.remove(user.getId());
  }
}
