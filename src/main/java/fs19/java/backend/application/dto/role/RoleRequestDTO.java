package fs19.java.backend.application.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.domain.entity.Company;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * This DTO will responsible to handle the request level of role related changes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleRequestDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Schema(type = "string", format = "string", description = "System roles name defines here")
    private String name;
    @NotNull
    @Schema(type = "string", format = "string", description = "Company Id defines here")
    private UUID companyId;
}
