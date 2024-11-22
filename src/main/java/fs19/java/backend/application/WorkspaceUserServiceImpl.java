package fs19.java.backend.application;

import fs19.java.backend.application.dto.workspace_user.WorkspaceUserRequestDTO;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserResponseDTO;
import fs19.java.backend.application.mapper.WorkspaceUserMapper;
import fs19.java.backend.application.service.WorkspaceUserService;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.WorkspaceUser;

import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceUserJpaRepo;
import fs19.java.backend.presentation.shared.exception.WorkspaceUserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceUserServiceImpl implements WorkspaceUserService {

  private final WorkspaceUserJpaRepo workspaceUserRepository;
  @Autowired
  private UserJpaRepo userRepository;
  @Autowired
  private RoleJpaRepo roleRepository;
  @Autowired
  private WorkspaceJpaRepo workspaceRepository;

  private static final String ERROR_MESSAGE = "Workspace User not found with ID ";

  public WorkspaceUserServiceImpl(WorkspaceUserJpaRepo workspaceUsersRepository) {
    this.workspaceUserRepository = workspaceUsersRepository;
  }

  @Override
  public WorkspaceUserResponseDTO createWorkspaceUser(WorkspaceUserRequestDTO workspaceUsersDTO) {
    validateWorkspaceUserRequestDTO(workspaceUsersDTO);

    User user = findUserById(workspaceUsersDTO.getUserId());
    Role role = findRoleById(workspaceUsersDTO.getRoleId());
    Workspace workspace = findWorkspaceById(workspaceUsersDTO.getWorkspaceId());

    WorkspaceUser workspaceUser = WorkspaceUserMapper.toEntity(workspaceUsersDTO, user, role, workspace);
    workspaceUser.setId(UUID.randomUUID());
    workspaceUser.setUser(user);
    workspaceUser.setRole(role);
    workspaceUser.setWorkspace(workspace);

    workspaceUserRepository.save(workspaceUser);
    return WorkspaceUserMapper.toDTO(workspaceUser);
  }

  @Override
  public WorkspaceUserResponseDTO updateWorkspaceUser(UUID id, WorkspaceUserRequestDTO workspaceUsersDTO) {
    Optional<WorkspaceUser> existingWorkspaceUsers = workspaceUserRepository.findById(id);
    if (existingWorkspaceUsers.isPresent()) {
      WorkspaceUser workspaceUsers = existingWorkspaceUsers.get();

      if (workspaceUsersDTO.getRoleId() != null) {
        Role role = findRoleById(workspaceUsersDTO.getRoleId());
        workspaceUsers.setRole(role);
      }
      if (workspaceUsersDTO.getWorkspaceId() != null) {
        Workspace workspace = findWorkspaceById(workspaceUsersDTO.getWorkspaceId());
        workspaceUsers.setWorkspace(workspace);
      }
      if (workspaceUsersDTO.getUserId() != null) {
        User user = findUserById(workspaceUsersDTO.getUserId());
        workspaceUsers.setUser(user);
      }

      workspaceUserRepository.save(workspaceUsers);
      return WorkspaceUserMapper.toDTO(workspaceUsers);
    }
    throw new WorkspaceUserNotFoundException(ERROR_MESSAGE + id);
  }

  @Override
  public WorkspaceUserResponseDTO getWorkspaceUserById(UUID id) {
    Optional<WorkspaceUser> workspaceUsers = workspaceUserRepository.findById(id);
    return workspaceUsers.map(WorkspaceUserMapper::toDTO)
        .orElseThrow(() -> new WorkspaceUserNotFoundException(ERROR_MESSAGE + id));
  }

  @Override
  public List<WorkspaceUserResponseDTO> getAllWorkspacesUsers() {
    return workspaceUserRepository.findAll().stream()
        .map(WorkspaceUserMapper::toDTO)
        .toList();
  }

  @Override
  public void deleteWorkspace(UUID id) {
    if (!workspaceUserRepository.existsById(id)) {
      throw new WorkspaceUserNotFoundException(ERROR_MESSAGE + id);
    }
    workspaceUserRepository.deleteById(id);
  }

  @Override
  public boolean existsById(UUID id) {
    if (workspaceUserRepository.existsById(id)) {
      workspaceUserRepository.deleteById(id);
    }
    return false;
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

  private User findUserById(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
  }

  private Role findRoleById(UUID roleId) {
    return roleRepository.findById(roleId)
        .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));
  }

  private Workspace findWorkspaceById(UUID workspaceId) {
    return workspaceRepository.findById(workspaceId)
        .orElseThrow(() -> new IllegalArgumentException("Workspace not found with ID: " + workspaceId));
  }
}