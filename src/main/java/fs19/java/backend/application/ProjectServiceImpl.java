package fs19.java.backend.application;

import fs19.java.backend.application.dto.project.ProjectCreateDTO;
import fs19.java.backend.application.dto.project.ProjectReadDTO;
import fs19.java.backend.application.dto.project.ProjectUpdateDTO;
import fs19.java.backend.application.mapper.ProjectMapper;
import fs19.java.backend.application.service.ProjectService;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;

import fs19.java.backend.infrastructure.JpaRepositories.ProjectJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.WorkspaceJpaRepo;
import fs19.java.backend.presentation.shared.exception.ProjectNotFoundException;
import fs19.java.backend.presentation.shared.exception.ProjectValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private final ProjectJpaRepo projectRepository;
    private final WorkspaceJpaRepo workspaceRepository;
    private final UserJpaRepo userRepository;

    private static final String ERROR_MESSAGE = "Project not found with Id ";

    public ProjectServiceImpl(ProjectJpaRepo projectRepository, UserJpaRepo userRepositor, WorkspaceJpaRepo workspaceRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepositor;
        this.workspaceRepository = workspaceRepository;
    }
    @Override
    public ProjectReadDTO createProject(ProjectCreateDTO projectDTO) {
        if (projectDTO.getName() == null || projectDTO.getName().isEmpty()) {
            throw new ProjectValidationException("Project name is required");
        }
        if (projectDTO.getStartDate() == null || projectDTO.getStartDate().isAfter(projectDTO.getEndDate())) {
            throw new ProjectValidationException("Start date is required and must be before end date");
        }
        if (projectDTO.getEndDate() == null || projectDTO.getEndDate().isBefore(projectDTO.getStartDate())) {
            throw new ProjectValidationException("End date is required and must be after start date");
        }

        User createdBy = userRepository.findById(projectDTO.getCreatedByUserId())
            .orElseThrow(() -> new ProjectValidationException("User not found with ID " + projectDTO.getCreatedByUserId()));

        Workspace workspace = workspaceRepository.findById(projectDTO.getWorkspaceId())
            .orElseThrow(() -> new ProjectValidationException(
                "Workspace not found with ID " + projectDTO.getWorkspaceId()));

        Project project = ProjectMapper.toEntity(projectDTO);
        project.setId(UUID.randomUUID());
        project.setCreatedDate(ZonedDateTime.now());
        project.setCreatedByUser(createdBy);
        project.setWorkspace(workspace);

        projectRepository.save(project);
        return ProjectMapper.toReadDTO(project);
    }

    @Override
    public ProjectReadDTO updateProject(UUID projectId, ProjectUpdateDTO projectDTO) {
        Optional<Project> existingProject = projectRepository.findById(projectId);

        if (existingProject.isPresent()) {
            Project updatedProject = existingProject.get();
            updatedProject.setDescription(projectDTO.getDescription());
            updatedProject.setStartDate(projectDTO.getStartDate());
            updatedProject.setEndDate(projectDTO.getEndDate());
            updatedProject.setStatus(projectDTO.getStatus());

            projectRepository.save(updatedProject);
            return ProjectMapper.toReadDTO(updatedProject);
        }
        else {
            throw new ProjectValidationException(ERROR_MESSAGE + projectId);
        }
    }

    @Override
    public Boolean deleteProject(UUID projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            projectRepository.delete(project.get());
            return true;
        }
        return false;
    }

    @Override
    public ProjectReadDTO findProjectById(UUID projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(ERROR_MESSAGE + projectId));
        return ProjectMapper.toReadDTO(project);
    }

    @Override
    public List<ProjectReadDTO> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
            .map(ProjectMapper::toReadDTO)
            .toList();
    }
}
