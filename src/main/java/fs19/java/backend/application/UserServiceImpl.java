package fs19.java.backend.application;

import fs19.java.backend.application.dto.UserDTO;
import fs19.java.backend.application.mapper.UserMapper;

import fs19.java.backend.application.service.UserService;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.UserRepositoryImpl;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserValidationException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepositoryImpl userRepository;
  private static final String ERROR_MESSAGE = "User not found with ID ";

  public UserServiceImpl(UserRepositoryImpl userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDTO createUser(UserDTO userDTO) {
    if (userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()) {
      throw new UserValidationException("First name is required");
    }
    if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()) {
      throw new UserValidationException("Last name is required");
    }
    if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
      throw new UserValidationException("Email is required");
    }
    if (userDTO.getPassword() != null && userDTO.getPassword().length() < 8) {
      throw new UserValidationException("Password must be at least 8 characters long");
    }

      User user = UserMapper.toEntity(userDTO);
    user.setId(UUID.randomUUID());
    user.setCreatedDate(ZonedDateTime.now());
    userRepository.saveUser(user);
     return UserMapper.toDTO(user);
  }

  @Override
  public List<UserDTO> findAllUsers() {
    List<User> users = userRepository.findAllUsers();
    return users.stream()
        .map(UserMapper::toDTO)
        .toList();

  }

  @Override
  public UserDTO findUserById(UUID id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(ERROR_MESSAGE + id));
    return UserMapper.toDTO(user);
  }

  @Override
  public UserDTO updateUser(UUID id, UserDTO userDTO) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      User updatedUser = user.get();
      updatedUser.setFirstName(userDTO.getFirstName());
      updatedUser.setLastName(userDTO.getLastName());
      updatedUser.setEmail(userDTO.getEmail());
      updatedUser.setPhone(userDTO.getPhone());
      updatedUser.setPassword(userDTO.getPassword());
      updatedUser.setPhone(userDTO.getPhone());
      updatedUser.setProfileImage(userDTO.getProfileImage());
      userRepository.saveUser(updatedUser);
      return UserMapper.toDTO(updatedUser);
    } else {
      throw new UserNotFoundException(ERROR_MESSAGE + id);
    }
  }

  @Override
  public boolean deleteUser(UUID id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      userRepository.deleteUser(user.get());
      return true;
    } else {
      throw new UserNotFoundException(ERROR_MESSAGE + id);
    }
  }
}
