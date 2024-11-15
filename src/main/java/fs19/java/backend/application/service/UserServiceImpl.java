package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.UserDTO;
import fs19.java.backend.application.mapper.UserMapper;

import fs19.java.backend.domain.entity.Users;
import fs19.java.backend.infrastructure.UserRepositoryImpl;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepositoryImpl userRepository;

  public UserServiceImpl(UserRepositoryImpl userRepository) {
    this.userRepository = userRepository;
  }

  private static final String ERROR_MESSAGE = "User not found with ID ";


  @Override
  public UserDTO createUser(UserDTO userDTO) {
    Users user = UserMapper.toEntity(userDTO);
    user.setId(UUID.randomUUID());
    user.setCreatedDate(ZonedDateTime.now());
    userRepository.saveUser(user);
     return UserMapper.toDTO(user);
  }

  @Override
  public List<UserDTO> findAllUsers() {
    List<Users> users = userRepository.findAllUsers();
    return users.stream()
        .map(UserMapper::toDTO)
        .toList();

  }

  @Override
  public UserDTO findUserById(UUID id) {
    Users user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(ERROR_MESSAGE + id));
    return UserMapper.toDTO(user);
  }

  @Override
  public UserDTO updateUser(UUID id, UserDTO userDTO) {
    Optional<Users> user = userRepository.findById(id);
    if (user.isPresent()) {
      Users updatedUser = user.get();
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
    Optional<Users> user = userRepository.findById(id);
    if (user.isPresent()) {
      userRepository.deleteUser(user.get());
      return true;
    } else {
      throw new UserNotFoundException(ERROR_MESSAGE + id);
    }
  }
}
