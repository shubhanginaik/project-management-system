package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface RolePermissionService {

    RolePermissionResponseDTO assignPermissionToRole(@Valid RolePermissionRequestDTO rolePermissionRequestDTO);

    RolePermissionResponseDTO updateRolePermission(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO);

    RolePermissionResponseDTO deleteRolePermission(UUID rolePermissionId);

    List<RolePermissionResponseDTO> getRolesByPermissionByPermissionId(UUID permissionId);

    RolePermissionResponseDTO getExistingRecord(UUID roleId, UUID permissionId);

    List<RolePermissionResponseDTO> getRolePermissions();

    RolePermissionResponseDTO getRolePermissionById(UUID rolePermissionId);

    List<RolePermissionResponseDTO> getPermissionByRoleId(UUID roleId);
}
