package fs19.java.backend.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Responsible to handle permission
 */
@AllArgsConstructor
public class RolePermission {

    private UUID id;
    @NotNull
    private Role role;
    @NotNull
    private Permission permission;
}
