package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.domain.entity.Permission;

import java.util.List;
import java.util.UUID;

/**
 * Define the permission repository
 */
public interface PermissionRepository {

    Permission createPermission(PermissionRequestDTO permissionRequestDTO);

    Permission updatePermission(UUID permissionId,PermissionRequestDTO permissionRequestDTO);

    Permission deletePermission(UUID permissionId);

    List<Permission> getPermissions();

    Permission getPermissionById(UUID permissionId);

    Permission getPermissionByName(String permissionName);


}
