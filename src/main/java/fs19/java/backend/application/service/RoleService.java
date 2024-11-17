package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

/**
 * Role service implementation
 */
public interface RoleService {

    RoleResponseDTO createRole(@Valid RoleRequestDTO roleRequestDTO);

    RoleResponseDTO updateRole(UUID roleId, @Valid RoleRequestDTO roleMDDTO);

    RoleResponseDTO deleteRole(UUID roleId);

    RoleResponseDTO getRoleById(UUID roleId);

    List<RoleResponseDTO> getRoles();

    RoleResponseDTO getRoleByName(String name);
}
