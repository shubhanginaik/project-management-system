package fs19.java.backend.application;

import fs19.java.backend.application.dto.user.UserCreateDto;
import fs19.java.backend.application.dto.user.UserReadDto;
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
  public UserReadDto createUser(UserCreateDto createUserDTO) {
    if (createUserDTO.getFirstName() == null || createUserDTO.getFirstName().isEmpty()) {
      throw new UserValidationException("First name is required");
    }
    if (createUserDTO.getLastName() == null || createUserDTO.getLastName().isEmpty()) {
      throw new UserValidationException("Last name is required");
    }
    if (createUserDTO.getEmail() == null || createUserDTO.getEmail().isEmpty()) {
      throw new UserValidationException("Email is required");
    }
    if (createUserDTO.getPassword() != null && createUserDTO.getPassword().length() < 8) {
      throw new UserValidationException("Password must be at least 8 characters long");
    }
    if (createUserDTO.getPhone().length() > 15 || createUserDTO.getPhone().length() < 10) {
      throw new UserValidationException("Phone number must be between 10 and 15 characters long");
      }



    User user = UserMapper.toEntity(createUserDTO);
    user.setId(UUID.randomUUID());
    user.setCreatedDate(ZonedDateTime.now());
    userRepository.saveUser(user);
    return UserMapper.toReadDto(user);
  }

  @Override
  public List<UserReadDto> findAllUsers() {
    List<User> users = userRepository.findAllUsers();
    return users.stream()
        .map(UserMapper::toReadDto)
        .toList();

  }

  @Override
  public UserReadDto findUserById(UUID id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(ERROR_MESSAGE + id));
    return UserMapper.toReadDto(user);
  }

  @Override
  public UserReadDto updateUser(UUID id, UserReadDto userDTO) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      User updatedUser = user.get();
      updatedUser.setFirstName(userDTO.getFirstName());
      updatedUser.setLastName(userDTO.getLastName());
      updatedUser.setEmail(userDTO.getEmail());
      updatedUser.setPhone(userDTO.getPhone());
      updatedUser.setProfileImage(userDTO.getProfileImage());
      userRepository.saveUser(updatedUser);
      return UserMapper.toReadDto(updatedUser);
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
