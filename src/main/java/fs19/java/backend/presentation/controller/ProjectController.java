package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.ProjectServiceImpl;
import fs19.java.backend.application.dto.project.ProjectDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/projects")
public class ProjectController {

  private final ProjectServiceImpl projectService;

  public ProjectController(ProjectServiceImpl projectService) {
    this.projectService = projectService;
  }

  @PostMapping
  public ResponseEntity<GlobalResponse<ProjectDTO>> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
    ProjectDTO createdProject = projectService.createProject(projectDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdProject),
        HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<GlobalResponse<List<ProjectDTO>>> getAllProjects() {
    List<ProjectDTO> projects = projectService.findAllProjects();
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), projects),
        HttpStatus.OK);
  }

  @GetMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<ProjectDTO>> getProjectById(@PathVariable UUID projectId) {
    ProjectDTO project = projectService.findProjectById(projectId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), project),
        HttpStatus.OK);
  }

  @PutMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<ProjectDTO>> updateProject(@PathVariable UUID projectId, @Valid @RequestBody ProjectDTO projectToUpdateDTO) {
    ProjectDTO updatedProject = projectService.updateProject(projectId, projectToUpdateDTO);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedProject),
        HttpStatus.OK);
  }

  @DeleteMapping("/{projectId}")
  public ResponseEntity<GlobalResponse<Void>> deleteProject(@PathVariable UUID projectId) {
    //find project by id and delete it
    Optional<ProjectDTO> project = Optional.ofNullable(projectService.findProjectById(projectId));
    if (project.isEmpty()) {
      return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NOT_FOUND.value(), null),
          HttpStatus.NOT_FOUND);
    }
    projectService.deleteProject(projectId);
    return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null),
        HttpStatus.NO_CONTENT);
  }
}
