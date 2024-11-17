package fs19.java.backend.application.dto.workspace;

import fs19.java.backend.domain.entity.WorkspaceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class WorkspaceUpdateDTO {

    @Schema(type = "string", format = "string", description = "Name of the workspace")
    private Optional<String> name = Optional.empty();

    @Schema(type = "string", format = "string", description = "Description of the workspace")
    private Optional<String> description = Optional.empty();

    @Schema(type = "string", format = "string", description = "Type of the workspace")
    private Optional<WorkspaceType> type = Optional.empty();

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the company")
    private Optional<UUID> companyId = Optional.empty();
}