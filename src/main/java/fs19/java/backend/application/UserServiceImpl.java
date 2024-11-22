package fs19.java.backend.application;

import fs19.java.backend.application.dto.user.UserCreateDTO;
import fs19.java.backend.application.dto.user.UserReadDTO;
import fs19.java.backend.application.mapper.UserMapper;
import fs19.java.backend.application.service.UserService;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserValidationException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserJpaRepo userRepository;
  private static final String ERROR_MESSAGE = "User not found with ID ";

  public UserServiceImpl(UserJpaRepo userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserReadDTO createUser(UserCreateDTO createUserDTO) {
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
    userRepository.save(user);
    return UserMapper.toReadDTO(user);
  }

  @Override
  public List<UserReadDTO> findAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(UserMapper::toReadDTO)
        .toList();

  }

  @Override
  public UserReadDTO findUserById(UUID id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(ERROR_MESSAGE + id));
    return UserMapper.toReadDTO(user);
  }

  @Override
  public UserReadDTO updateUser(UUID id, UserReadDTO userDTO) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      User updatedUser = user.get();
      updatedUser.setFirstName(userDTO.getFirstName());
      updatedUser.setLastName(userDTO.getLastName());
      updatedUser.setEmail(userDTO.getEmail());
      updatedUser.setPhone(userDTO.getPhone());
      updatedUser.setProfileImage(userDTO.getProfileImage());
      userRepository.save(updatedUser);
      return UserMapper.toReadDTO(updatedUser);
    } else {
      throw new UserNotFoundException(ERROR_MESSAGE + id);
    }
  }

  @Override
  public boolean deleteUser(UUID id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      userRepository.delete(user.get());
      return true;
    } else {
      throw new UserNotFoundException(ERROR_MESSAGE + id);
    }
  }
}
