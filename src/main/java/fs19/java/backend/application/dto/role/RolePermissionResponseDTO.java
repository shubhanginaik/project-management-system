package fs19.java.backend.application.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * This DTO will responsible to handle the response level of role-permission related changes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePermissionResponseDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Schema(type = "RoleResponseDTO", format = "object", description = "Role Object ")
    @NotNull
    private RoleResponseDTO roleResponseDTO;
    @Schema(type = "permissionResponseDTO", format = "object", description = "Permission Object")
    @NotNull
    private PermissionResponseDTO permissionResponseDTO;
    @Schema(type = "String", format = "ResponseStatus", description = "Unique system status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ResponseStatus status;
}
