package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersRequestDTO;
import fs19.java.backend.application.dto.workspace_users.WorkspaceUsersResponseDTO;
import fs19.java.backend.domain.entity.WorkspaceUsers;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceUsersMapper {

  public static WorkspaceUsers toEntity(WorkspaceUsersRequestDTO workspaceUsersDTO) {
    WorkspaceUsers workspaceUsers = new WorkspaceUsers();
    workspaceUsers.setId(workspaceUsersDTO.getId());
    workspaceUsers.setRoleId(workspaceUsersDTO.getRoleId());
    workspaceUsers.setUserId(workspaceUsersDTO.getUserId());
    workspaceUsers.setWorkspaceId(workspaceUsersDTO.getWorkspaceId());
    return workspaceUsers;
  }

  public static WorkspaceUsersResponseDTO toDTO(@NotNull WorkspaceUsers workspaceUsers) {
    WorkspaceUsersResponseDTO workspaceUsersDTO = new WorkspaceUsersResponseDTO();
    workspaceUsersDTO.setId(workspaceUsers.getId());
    workspaceUsersDTO.setRoleId(workspaceUsers.getRoleId());
    workspaceUsersDTO.setUserId(workspaceUsers.getUserId());
    workspaceUsersDTO.setWorkspaceId(workspaceUsers.getWorkspaceId());
    return workspaceUsersDTO;
  }
}
