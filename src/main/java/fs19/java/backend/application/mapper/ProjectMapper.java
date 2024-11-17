package fs19.java.backend.application.mapper;

import static java.util.UUID.fromString;

import fs19.java.backend.application.dto.project.ProjectDTO;
import fs19.java.backend.domain.entity.Project;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectMapper {

  public static Project toEntity(ProjectDTO dto) {
    Project project = new Project();
    project.setId(dto.getId());
    project.setName(dto.getName());
    project.setDescription(dto.getDescription());
    project.setCreatedDate(dto.getCreatedDate());
    project.setStartDate(dto.getStartDate());
    project.setEndDate(dto.getEndDate());
    project.setCreatedByUserId(dto.getCreatedByUserId());
    project.setWorkspaceId(dto.getWorkspaceId());
    project.setStatus(dto.getStatus());
    return project;
  }

  public static ProjectDTO toDTO(Project project) {
    ProjectDTO dto = new ProjectDTO();
    dto.setId(project.getId());
    dto.setName(project.getName());
    dto.setDescription(project.getDescription());
    dto.setCreatedDate(project.getCreatedDate());
    dto.setStartDate(project.getStartDate());
    dto.setEndDate(project.getEndDate());
    dto.setCreatedByUserId(project.getCreatedByUserId());
    dto.setWorkspaceId(project.getWorkspaceId());
    dto.setStatus(project.getStatus());
    return dto;
  }
}
