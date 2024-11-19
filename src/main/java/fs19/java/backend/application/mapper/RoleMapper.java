package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.domain.entity.Role;
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
