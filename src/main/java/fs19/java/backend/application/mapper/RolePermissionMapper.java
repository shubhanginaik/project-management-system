package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.presentation.shared.status.ResponseStatus;

public class RolePermissionMapper {

    /**
     * Convert role permission object into response
     * @param rolePermission
     * @param status
     * @return
     */
    public static RolePermissionResponseDTO toPermissionResponseDTO(RolePermission rolePermission, ResponseStatus status) {
        return new RolePermissionResponseDTO(rolePermission.getId(), RoleMapper.toRoleResponseDTO(rolePermission.getRole(), status), PermissionMapper.toPermissionResponseDTO(rolePermission.getPermission(), status), status);
    }

}
