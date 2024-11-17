package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.domain.entity.Workspace;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceMapper {

    public Workspace toEntity(WorkspaceRequestDTO workspaceRequestDTO ) {
        return new Workspace(
                null,
                workspaceRequestDTO.getName(),
                workspaceRequestDTO.getDescription(),
                workspaceRequestDTO.getType(),
                null,
                workspaceRequestDTO.getCreatedBy(),
                workspaceRequestDTO.getCompanyId()
        );
    }

    public WorkspaceResponseDTO toDTO(Workspace workspace) {
        return new WorkspaceResponseDTO(
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription(),
                workspace.getType(),
                workspace.getCreatedDate(),
                workspace.getCreatedBy(),
                workspace.getCompanyId()
        );
    }
}