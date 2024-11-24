package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.WorkspaceUserServiceImpl;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserRequestDTO;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserResponseDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/workspace-users")
public class WorkspaceUserController {

  private final WorkspaceUserServiceImpl workspaceUsersService;

  public WorkspaceUserController(WorkspaceUserServiceImpl workspaceUsersService) {
    this.workspaceUsersService = workspaceUsersService;
  }

  @PostMapping
  public ResponseEntity<GlobalResponse<WorkspaceUserResponseDTO>> createWorkspaceUser(
      @Valid @RequestBody WorkspaceUserRequestDTO workspaceUsersDTO) {
    // validate the request
    validateWorkspaceUserRequestDTO(workspaceUsersDTO);
    // create the workspace user
    WorkspaceUserResponseDTO workspaceUsersResponseDTO = workspaceUsersService.createWorkspaceUser(
        workspaceUsersDTO);
    // return the response
    return new ResponseEntity<>(
        new GlobalResponse<>(HttpStatus.CREATED.value(), workspaceUsersResponseDTO),
        HttpStatus.CREATED);
  }

  //
  @GetMapping
  public ResponseEntity<GlobalResponse<List<WorkspaceUserResponseDTO>>> getAllWorkspaceUsers() {
    List<WorkspaceUserResponseDTO> workspaceUsers = workspaceUsersService.getAllWorkspacesUsers();
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers),
        HttpStatus.OK);
  }

  @GetMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<WorkspaceUserResponseDTO>> getWorkspaceUserById(
      @PathVariable UUID workspaceUserId) {
    WorkspaceUserResponseDTO workspaceUsers = workspaceUsersService.getWorkspaceUserById(
        workspaceUserId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers),
        HttpStatus.OK);
  }

  @PutMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<WorkspaceUserResponseDTO>> updateWorkspaceUser(
      @PathVariable UUID workspaceUserId,
      @Valid @RequestBody WorkspaceUserRequestDTO workspaceUsersDTO) {
    WorkspaceUserResponseDTO workspaceUsers = workspaceUsersService.updateWorkspaceUser(
        workspaceUserId, workspaceUsersDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaceUsers),
        HttpStatus.OK);
  }

  @DeleteMapping("/{workspaceUserId}")
  public ResponseEntity<GlobalResponse<String>> deleteWorkspaceUser(
      @PathVariable UUID workspaceUserId) {
    workspaceUsersService.deleteWorkspace(workspaceUserId);
    return new ResponseEntity<>(
        new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), "Workspace User deleted successfully"),
        HttpStatus.OK);
  }

  private void validateWorkspaceUserRequestDTO(WorkspaceUserRequestDTO workspaceUsersDTO) {
    if (workspaceUsersDTO.getRoleId() == null) {
      throw new IllegalArgumentException("Role ID is required");
    }
    if (workspaceUsersDTO.getWorkspaceId() == null) {
      throw new IllegalArgumentException("Workspace ID is required");
    }
    if (workspaceUsersDTO.getUserId() == null) {
      throw new IllegalArgumentException("User ID is required");
    }
  }
}
