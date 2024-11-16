package fs19.java.backend.application.dto.workspace;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.domain.entity.WorkspaceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceRequestDTO {

    @Schema(type = "string", format = "string", description = "Name of the workspace", example = "Test Workspace")
    @NotBlank(message = "Workspace name is required")
    @Size(min = 3, max = 45, message = "Workspace name must be between 3 and 50 characters")
    private String name;

    @Schema(type = "string", format = "string", description = "Description of the workspace", example = "Test Description")
    @Size(max = 100, message = "Workspace description must be less than 100 characters")
    private String description;

    @Schema(type = "string", format = "string", description = "Type of the workspace")
    @NotNull(message = "Workspace type is required")
    private WorkspaceType type;

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the user who created the workspace")
    @NotNull(message = "Created by user ID is required")
    private UUID createdBy;

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the company")
    @NotNull(message = "Company ID is required")
    private UUID companyId;
}
