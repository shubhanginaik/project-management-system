package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.domain.abstraction.PermissionRepository;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.infrastructure.tempMemory.RoleInMemoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 *
 * Permission Repo implementation
 *
 */
@Repository
public class PermissionRepoImpl implements PermissionRepository {

    @Autowired
    private RoleInMemoryDB tempRoleDB;

    /**
     * Create permission
     * @param permissionRequestDTO
     * @return
     */
    @Override
    public Permission createPermission(PermissionRequestDTO permissionRequestDTO) {
        return tempRoleDB.createPermission(permissionRequestDTO);
    }

    /**
     * Update permission
     * @param permissionId
     * @param permissionRequestDTO
     * @return
     */
    @Override
    public Permission updatePermission(UUID permissionId, PermissionRequestDTO permissionRequestDTO) {
        return tempRoleDB.updatePermission(permissionId, permissionRequestDTO);
    }

    /**
     * Delete permission
     * @param permissionId
     * @return
     */
    @Override
    public Permission deletePermission(UUID permissionId) {
        return tempRoleDB.deletePermission(permissionId);
    }

    /**
     * Load all permissions
     * @return
     */
    @Override
    public List<Permission> getPermissions() {
        return tempRoleDB.finaAllPermissions();
    }

    /**
     * Load permission by permission Id
     * @param permissionId
     * @return
     */
    @Override
    public Permission getPermissionById(UUID permissionId) {
        return tempRoleDB.findPermissionById(permissionId);
    }

    /**
     * Load permission by permission Name
     * @param permissionName
     * @return
     */
    @Override
    public Permission getPermissionByName(String permissionName) {
        return tempRoleDB.findPermissionByName(permissionName);
    }

}
