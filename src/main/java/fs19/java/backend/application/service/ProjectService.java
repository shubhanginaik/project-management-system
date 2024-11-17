package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.project.ProjectDTO;
import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);
    void updateProject(UUID projectId, ProjectDTO projectDTO);
    Boolean deleteProject(UUID projectId);
    void findProjectById();
    List<ProjectDTO> findAllUsers();



}
