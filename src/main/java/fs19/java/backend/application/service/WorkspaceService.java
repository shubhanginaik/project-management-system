package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface WorkspaceService {
    WorkspaceResponseDTO createWorkspace(WorkspaceRequestDTO workspaceRequestDTO);
    WorkspaceResponseDTO updateWorkspace(UUID id, WorkspaceUpdateDTO workspaceUpdateDTO);
    WorkspaceResponseDTO getWorkspaceById(UUID id);
    List<WorkspaceResponseDTO> getAllWorkspaces();
    void deleteWorkspace(UUID id);
}