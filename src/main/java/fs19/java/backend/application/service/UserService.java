package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.user.UserCreateDTO;
import fs19.java.backend.application.dto.user.UserReadDTO;
import java.util.List;
import java.util.UUID;

public interface UserService {

 UserReadDTO createUser(UserCreateDTO userDTO);
  List<UserReadDTO> findAllUsers();
  UserReadDTO findUserById(UUID id);
  UserReadDTO updateUser(UUID id, UserReadDTO userDTO);
  boolean deleteUser(UUID id);
}
