package fs19.java.backend.application;

import fs19.java.backend.application.dto.project.ProjectDTO;
import fs19.java.backend.application.service.ProjectService;
import java.util.List;
import java.util.UUID;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        return null;
    }

    @Override
    public void updateProject(UUID projectId, ProjectDTO projectDTO) {

    }

    @Override
    public Boolean deleteProject(UUID projectId) {
      return null;
    }

    @Override
    public void findProjectById() {
    }

    @Override
    public List<ProjectDTO> findAllUsers() {
      return null;
    }



}
