package fs19.java.backend.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Responsible to handle permission
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RolePermission {

    private UUID id;
    @NotNull
    private UUID roleId;
    @NotNull
    private UUID permissionId;
}
