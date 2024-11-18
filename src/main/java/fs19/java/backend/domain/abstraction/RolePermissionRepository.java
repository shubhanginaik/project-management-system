package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.domain.entity.RolePermission;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Role permission repository
 */
public interface RolePermissionRepository {

    RolePermissionResponseDTO createRolePermission(RolePermissionRequestDTO rolePermissionRequestDTO);

    List<RolePermissionResponseDTO> getRolePermissions();

    RolePermissionResponseDTO deleteRolePermission(UUID rolePermissionId);

    RolePermissionResponseDTO updateRolePermission(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO);

    RolePermissionResponseDTO validateWithExistingRecords(RolePermissionRequestDTO rolePermissionRequestDTO);

    RolePermissionResponseDTO getRolePermissionById(UUID id);

    List<RolePermissionResponseDTO> getPermissionByRoleId(UUID roleId);

    List<RolePermissionResponseDTO> getRolesByPermissionId(UUID permissionId);

    RolePermission getResultIfExist(@NotNull UUID roleId, @NotNull UUID permissionId);
}
