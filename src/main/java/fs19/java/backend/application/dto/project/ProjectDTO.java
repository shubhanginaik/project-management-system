package fs19.java.backend.application.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

  @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the project")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UUID id;

  @Schema(type = "string", format = "string", description = "Name of the project", example = "Test Project")
  @Size(min = 3, max = 45, message = "Project name must be between 3 and 50 characters")
  @NotNull(message = "Project name is required")
  private String name;

  @Schema(type = "string", format = "string", description = "Description of the project", example = "Test Description")
  @Size(max = 100, message = "Project description must be less than 100 characters")
  private String description;

  @Schema(type = "string", format = "string", description = "Start date of the project")
  @NotNull(message = "Start date is required")
  private ZonedDateTime startDate;

  @Schema(type = "string", format = "string", description = "End date of the project")
  @NotNull(message = "End date is required")
  private ZonedDateTime endDate;

  @Schema(type = "string", format = "string", description = "Creation date of the project")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private ZonedDateTime createdDate;

  @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the user who created the project")
  private UUID createdByUserId;

  @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the workspace")
  private UUID workspaceId;

  @Schema(type = "Boolean", format = "Boolean", description = "Status of the project")
  @NotNull(message = "Status is required")
  private Boolean status;
}
