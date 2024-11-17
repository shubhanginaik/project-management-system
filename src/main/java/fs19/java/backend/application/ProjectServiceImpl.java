package fs19.java.backend.application;

import fs19.java.backend.application.dto.project.ProjectDTO;
import fs19.java.backend.application.mapper.ProjectMapper;
import fs19.java.backend.application.service.ProjectService;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.infrastructure.ProjectRepositoryImpl;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepositoryImpl projectRepository;

    public ProjectServiceImpl(ProjectRepositoryImpl projectRepository) {
        this.projectRepository = projectRepository;
    }
    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        //TODO: Implement validation
        Project project = ProjectMapper.toEntity(projectDTO);
        project.setId(UUID.randomUUID());
        project.setCreatedDate(ZonedDateTime.now());
        projectRepository.saveProject(project);
        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO updateProject(UUID projectId, ProjectDTO projectDTO) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            Project updatedProject = project.get();
            updatedProject.setName(projectDTO.getName());
            updatedProject.setDescription(projectDTO.getDescription());
            updatedProject.setStartDate(projectDTO.getStartDate());
            updatedProject.setEndDate(projectDTO.getEndDate());
            updatedProject.setCreatedByUserId(projectDTO.getCreatedByUserId());
            updatedProject.setWorkspaceId(projectDTO.getWorkspaceId());
            updatedProject.setStatus(projectDTO.getStatus());
            projectRepository.saveProject(updatedProject);
            return ProjectMapper.toDTO(updatedProject);
        }
        else {
            return null;
        }
    }

    @Override
    public Boolean deleteProject(UUID projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            projectRepository.deleteProject(project.get());
            return true;
        }
        return false;
    }

    @Override
    public ProjectDTO findProjectById(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID " + projectId));
        return ProjectMapper.toDTO(project);
    }

    @Override
    public List<ProjectDTO> findAllProjects() {
        List<Project> projects = projectRepository.findAllProjects();
        return projects.stream()
                .map(ProjectMapper::toDTO)
                .toList();
    }
}
