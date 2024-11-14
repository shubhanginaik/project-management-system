package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.UserDTO;
import java.util.List;
import java.util.UUID;

public interface UserService {

  UserDTO createUser(UserDTO userDTO);
  List<UserDTO> findAllUsers();
  UserDTO findUserById(UUID id);
  UserDTO updateUser(UUID id, UserDTO userDTO);
  boolean deleteUser(UUID id);
}
