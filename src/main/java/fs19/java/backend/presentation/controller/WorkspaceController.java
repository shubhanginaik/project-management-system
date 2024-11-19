package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.workspace.WorkspaceRequestDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceResponseDTO;
import fs19.java.backend.application.dto.workspace.WorkspaceUpdateDTO;
import fs19.java.backend.application.service.WorkspaceService;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Workspaces", description = "Manage workspaces")
@RestController
@RequestMapping("/v1/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @Operation(summary = "Create a workspace", description = "Creates a new workspace with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<WorkspaceResponseDTO>> createWorkspace(@Valid @RequestBody WorkspaceRequestDTO workspaceRequestDTO) {
        WorkspaceResponseDTO createdWorkspace = workspaceService.createWorkspace(workspaceRequestDTO);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdWorkspace), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a workspace", description = "Updates the details of an existing workspace.")
    @PutMapping("/{workspaceId}")
    public ResponseEntity<GlobalResponse<WorkspaceResponseDTO>> updateWorkspace(@PathVariable UUID workspaceId, @Valid @RequestBody WorkspaceUpdateDTO workspaceUpdateDTO) {
        WorkspaceResponseDTO updatedWorkspace = workspaceService.updateWorkspace(workspaceId, workspaceUpdateDTO);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedWorkspace), HttpStatus.OK);
    }

    @Operation(summary = "Get a workspace by ID", description = "Retrieves the details of a workspace by its ID.")
    @GetMapping("/{workspaceId}")
    public ResponseEntity<GlobalResponse<WorkspaceResponseDTO>> getWorkspaceById(@PathVariable UUID workspaceId) {
        WorkspaceResponseDTO workspace = workspaceService.getWorkspaceById(workspaceId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspace), HttpStatus.OK);
    }

    @Operation(summary = "Get all workspaces", description = "Retrieves the details of all workspaces.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<WorkspaceResponseDTO>>> getAllWorkspaces() {
        List<WorkspaceResponseDTO> workspaces = workspaceService.getAllWorkspaces();
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), workspaces), HttpStatus.OK);
    }

    @Operation(summary = "Delete a workspace", description = "Deletes a workspace by its ID.")
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<GlobalResponse<Void>> deleteWorkspace(@PathVariable UUID workspaceId) {
        workspaceService.deleteWorkspace(workspaceId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }
}