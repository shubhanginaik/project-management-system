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

  public WorkspaceUserResponseDTO createWorkspaceUser(WorkspaceUserRequestDTO workspaceUsersDTO) {
    if (workspaceUsersDTO.getRoleId() == null) {
      throw new IllegalArgumentException("Role ID is required");
    }
    if (workspaceUsersDTO.getWorkspaceId() == null) {
      throw new IllegalArgumentException("Workspace ID is required");
    }
    if (workspaceUsersDTO.getUserId() == null) {
      throw new IllegalArgumentException("User ID is required");
    }


    User user = userRepository.findById(workspaceUsersDTO.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + workspaceUsersDTO.getUserId()));

    Role role = roleRepository.findById(workspaceUsersDTO.getRoleId())
        .orElseThrow(() -> new IllegalArgumentException("Role not found with ID" + workspaceUsersDTO.getRoleId()));

    Workspace workspace = workspaceRepository.findById(workspaceUsersDTO.getWorkspaceId())
        .orElseThrow(() -> new IllegalArgumentException("Workspace not found with ID" + workspaceUsersDTO.getWorkspaceId()));

    WorkspaceUser workspaceUser = WorkspaceUserMapper.toEntity(workspaceUsersDTO, user, role, workspace);
    workspaceUser.setId(UUID.randomUUID());
    workspaceUser.setUser(user);
    workspaceUser.setRole(role);
    workspaceUser.setWorkspace(workspace);


    workspaceUserRepository.save(workspaceUser);
    return WorkspaceUserMapper.toDTO(workspaceUser);
  }


  public WorkspaceUserResponseDTO updateWorkspaceUser(UUID id, WorkspaceUserRequestDTO workspaceUsersDTO) {
    Optional<WorkspaceUser> existingWorkspaceUsers = workspaceUserRepository.findById(id);
    if (existingWorkspaceUsers.isPresent()) {
      WorkspaceUser workspaceUsers = existingWorkspaceUsers.get();

      if (workspaceUsersDTO.getRoleId() != null) {
        Role role = roleRepository.findById(workspaceUsersDTO.getRoleId())
            .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + workspaceUsersDTO.getRoleId()));
        workspaceUsers.setRole(role);
      }
      if (workspaceUsersDTO.getWorkspaceId() != null) {
        Workspace workspace = workspaceRepository.findById(workspaceUsersDTO.getWorkspaceId())
            .orElseThrow(() -> new IllegalArgumentException("Workspace not found with ID: " + workspaceUsersDTO.getWorkspaceId()));
        workspaceUsers.setWorkspace(workspace);
      }
      if (workspaceUsersDTO.getUserId() != null) {
        User user = userRepository.findById(workspaceUsersDTO.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + workspaceUsersDTO.getUserId()));
        workspaceUsers.setUser(user);
      }

      workspaceUserRepository.save(workspaceUsers);
      return WorkspaceUserMapper.toDTO(workspaceUsers);
    }
    throw  new WorkspaceUserNotFoundException(ERROR_MESSAGE + id);
  }

  public WorkspaceUserResponseDTO getWorkspaceUserById(UUID id) {
    Optional<WorkspaceUser> workspaceUsers = workspaceUserRepository.findById(id);
    return workspaceUsers.map(WorkspaceUserMapper::toDTO)
        .orElseThrow(() -> new WorkspaceUserNotFoundException(ERROR_MESSAGE + id));
  }

  public List<WorkspaceUserResponseDTO> getAllWorkspacesUsers() {
    return workspaceUserRepository.findAll().stream()
        .map(WorkspaceUserMapper::toDTO)
        .toList();
  }

  public void deleteWorkspace(UUID id) {
    if (!workspaceUserRepository.existsById(id)) {
      throw new WorkspaceUserNotFoundException(ERROR_MESSAGE + id);
    }
    workspaceUserRepository.deleteById(id);
  }

  public boolean existsById(UUID id) {
    if (workspaceUserRepository.existsById(id)) {
      workspaceUserRepository.deleteById(id);
    }
    return false;
  }
}
