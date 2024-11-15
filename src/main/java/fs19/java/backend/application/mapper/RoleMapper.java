package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.presentation.shared.status.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper {

    public RoleRequestDTO toRoleRequestDTO(Role role) {
        return new RoleRequestDTO(role.getId(), role.getName(), role.getCreated_date());
    }

    public RoleResponseDTO toRoleResponseDTO(Role role, ResponseStatus status) {
        return new RoleResponseDTO(role.getId(), role.getName(), role.getCreated_date(), status);
    }

    public List<RoleResponseDTO> toRoleResponseDTOs(List<Role> roles, ResponseStatus status) {
        List<RoleResponseDTO> responseDTOS = new ArrayList<>();
        roles.forEach(role -> {
            responseDTOS.add(toRoleResponseDTO(role, status));
        });
        return responseDTOS;
    }
}