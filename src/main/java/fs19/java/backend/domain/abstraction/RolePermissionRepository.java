package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;

/**
 * Role permission repository
 */
public interface RolePermissionRepository {

    RolePermissionResponseDTO createRolePermission(RolePermissionRequestDTO rolePermissionRequestDTO);
}
