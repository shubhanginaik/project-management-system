package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.presentation.shared.status.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will ensure the role related mapping
 */
public class RoleMapper {

    /**
     * Convert role into Role response dto
     *
     * @param role
     * @return
     */
    public static RoleResponseDTO toRoleResponseDTO(Role role, ResponseStatus status) {
        return new RoleResponseDTO(role.getId(), role.getName(), role.getCreated_date(), status);
    }

    /**
     * Convert role list into role response dto list
     *
     * @param roles
     * @param status
     * @return
     */
    public static List<RoleResponseDTO> toRoleResponseDTOs(List<Role> roles, ResponseStatus status) {
        List<RoleResponseDTO> responseDTOS = new ArrayList<>();
        roles.forEach(role -> {
            responseDTOS.add(toRoleResponseDTO(role, status));
        });
        return responseDTOS;
    }


}
