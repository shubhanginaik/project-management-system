package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.Users;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserRepo {
  void saveUser(Users user);
  List<Users> findAllUsers();
  Optional<Users> findById(UUID id);
  void deleteUser(Users user);
}
