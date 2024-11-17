package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

/**
 * Permission service interface
 */
public interface PermissionService {

    PermissionResponseDTO createPermission(@Valid PermissionRequestDTO permissionRequestDTO);

    PermissionResponseDTO updatePermission(UUID permissionId, @Valid PermissionRequestDTO permissionRequestDTO);

    PermissionResponseDTO deletePermission(UUID permissionId);

    List<PermissionResponseDTO> getPermissions();

    PermissionResponseDTO getPermissionById(UUID permissionId);

    PermissionResponseDTO getPermissionByName(String name);

}
