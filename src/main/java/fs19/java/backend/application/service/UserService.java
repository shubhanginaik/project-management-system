package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.user.UserCreateDto;
import fs19.java.backend.application.dto.user.UserReadDto;
import java.util.List;
import java.util.UUID;

public interface UserService {

 UserReadDto createUser(UserCreateDto userDTO);
  List<UserReadDto> findAllUsers();
  UserReadDto findUserById(UUID id);
  UserReadDto updateUser(UUID id, UserReadDto userDTO);
  boolean deleteUser(UUID id);
}
