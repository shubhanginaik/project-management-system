package fs19.java.backend.application;

import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceUpdateDTO;
import fs19.java.backend.application.mapper.WorkspaceMapper;
import fs19.java.backend.application.service.WorkspaceService;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.presentation.shared.exception.CompanyNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
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
    private static final String COMPANY_NOT_FOUND_MESSAGE = "Company with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with ID %s";

    private final WorkspaceJpaRepo workspaceRepository;
    private final WorkspaceMapper workspaceMapper;
    private final UserJpaRepo userRepository;
    private final CompanyJpaRepo companyRepository;

    public WorkspaceServiceImpl(WorkspaceJpaRepo workspaceRepository, WorkspaceMapper workspaceMapper, UserJpaRepo userRepository, CompanyJpaRepo companyRepository) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public WorkspaceResponseDTO createWorkspace(WorkspaceRequestDTO workspaceRequestDTO) {
        if (workspaceRequestDTO == null) {
            throw new InvalidWorkspaceException("WorkspaceRequestDTO cannot be null.");
        }
        User createdBy = userRepository.findById(workspaceRequestDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, workspaceRequestDTO.getCreatedBy())));
        Company company = companyRepository.findById(workspaceRequestDTO.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, workspaceRequestDTO.getCompanyId())));

        Workspace workspace = workspaceMapper.toEntity(workspaceRequestDTO, createdBy, company);
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
        workspaceUpdateDTO.getCompanyId().ifPresent(companyId -> {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, companyId)));
            existingWorkspace.setCompanyId(company);
        });

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
