package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceUpdateDTO;
import fs19.java.backend.application.mapper.WorkspaceMapper;
import fs19.java.backend.domain.abstraction.WorkspaceRepository;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.presentation.shared.exception.WorkspaceNotFoundException;
import fs19.java.backend.presentation.shared.exception.InvalidWorkspaceException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private static final String WORKSPACE_NOT_FOUND_MESSAGE = "Workspace with ID %s not found";

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;

    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
    }

    @Override
    public WorkspaceResponseDTO createWorkspace(WorkspaceRequestDTO workspaceRequestDTO) {
        if (workspaceRequestDTO == null) {
            throw new InvalidWorkspaceException("WorkspaceRequestDTO cannot be null.");
        }

        Workspace workspace = workspaceMapper.toEntity(workspaceRequestDTO);
        workspace.setId(UUID.randomUUID());
        workspace.setCreatedDate(ZonedDateTime.now());
        workspaceRepository.save(workspace);
        return workspaceMapper.toDTO(workspace);
    }

    @Override
    public WorkspaceResponseDTO updateWorkspace(UUID id, WorkspaceUpdateDTO workspaceUpdateDTO) {
        if (workspaceUpdateDTO == null) {
            throw new InvalidWorkspaceException("WorkspaceUpdateDTO cannot be null.");
        }

        Workspace existingWorkspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(String.format(WORKSPACE_NOT_FOUND_MESSAGE, id)));

        boolean isUpdateProvided = workspaceUpdateDTO.getName().isPresent() ||
                workspaceUpdateDTO.getDescription().isPresent() ||
                workspaceUpdateDTO.getType().isPresent() ||
                workspaceUpdateDTO.getCompanyId().isPresent();

        if (!isUpdateProvided) {
            throw new InvalidWorkspaceException("At least one field must be provided to update the workspace.");
        }
        workspaceUpdateDTO.getName().ifPresent(name -> {
            if (name.trim().isEmpty()) {
                throw new InvalidWorkspaceException("Workspace name cannot be blank.");
            }
            existingWorkspace.setName(name);
        });

        workspaceUpdateDTO.getDescription().ifPresent(description -> {
            if (description.trim().isEmpty()) {
                throw new InvalidWorkspaceException("Workspace description cannot be blank.");
            }
            existingWorkspace.setDescription(description);
        });

        workspaceUpdateDTO.getType().ifPresent(existingWorkspace::setType);
        workspaceUpdateDTO.getCompanyId().ifPresent(existingWorkspace::setCompanyId);

        workspaceRepository.save(existingWorkspace);
        return workspaceMapper.toDTO(existingWorkspace);
    }

    @Override
    public WorkspaceResponseDTO getWorkspaceById(UUID id) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(String.format(WORKSPACE_NOT_FOUND_MESSAGE, id)));
        return workspaceMapper.toDTO(workspace);
    }

    @Override
    public List<WorkspaceResponseDTO> getAllWorkspaces() {
        return workspaceRepository.findAll().stream()
                .map(workspaceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorkspace(UUID id) {
        if (!workspaceRepository.existsById(id)) {
            throw new WorkspaceNotFoundException(String.format(WORKSPACE_NOT_FOUND_MESSAGE, id));
        }
        workspaceRepository.deleteById(id);
    }
}