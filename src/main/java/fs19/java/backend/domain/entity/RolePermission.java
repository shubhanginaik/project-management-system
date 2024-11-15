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
@Getter
@Setter
public class RolePermission {

    private UUID id;
    @NotNull
    private Role role;
    @NotNull
    private Permission permission;

    public RolePermission() {
        this.id = null;
        this.role = new Role();
        this.permission = new Permission();
    }
}
