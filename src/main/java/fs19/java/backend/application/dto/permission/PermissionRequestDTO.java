package fs19.java.backend.application.dto.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Responsible to handle the permission Requests
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionRequestDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Schema(type = "string", format = "string", description = "permission name defines here")
    private String name;

}
