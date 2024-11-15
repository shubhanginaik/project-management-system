package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.UserDTO;
import fs19.java.backend.application.service.UserServiceImpl;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {


  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<GlobalResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
    UserDTO createdUser = userService.createUser(userDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdUser), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<GlobalResponse<List<UserDTO>>> getAllUsers() {
    List<UserDTO> users = userService.findAllUsers();
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), users), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GlobalResponse<UserDTO>> getUserById(@PathVariable UUID id) {
    UserDTO user = userService.findUserById(id);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), user), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<GlobalResponse<UserDTO>> updateUser(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO) {
    UserDTO updatedUser = userService.updateUser(id, userDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedUser), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GlobalResponse<Void>> deleteUser(@PathVariable  UUID id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
  }
}
