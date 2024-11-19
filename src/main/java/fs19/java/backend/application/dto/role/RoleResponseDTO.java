package fs19.java.backend.application.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class RoleResponseDTO {

    @Schema(type = "string", format = "uuid", description = "Unique identifier")
    private UUID id;
    @NotNull(message = "Role name cannot be null")
    @Size(min = 1, max = 45, message = "Role name must be between 1 and 45 characters")
    private String name;
    @NotNull
    private ZonedDateTime createdDate;  // This date always fills
    @Schema(type = "string", format = "string", description = "Company Id defines here")
    private UUID companyId;
    @Schema(type = "String", format = "ResponseStatus", description = "Unique system status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ResponseStatus status;


}
