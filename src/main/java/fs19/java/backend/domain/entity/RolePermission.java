package fs19.java.backend.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * Responsible to handle permission
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePermission {

    private UUID id;
    @NotNull
    private Role role;
    @NotNull
    private Permission permission;
}
