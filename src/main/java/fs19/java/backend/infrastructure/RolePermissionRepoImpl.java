package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.mapper.RolePermissionMapper;
import fs19.java.backend.domain.abstraction.RolePermissionRepository;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.tempMemory.RoleInMemoryDB;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Role-Permission Repo implementation
 */
@Repository
public class RolePermissionRepoImpl implements RolePermissionRepository {

    @Autowired
    private RoleInMemoryDB tempRoleDB;

    /**
     * create role permission by using user info
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO createRolePermission(RolePermissionRequestDTO rolePermissionRequestDTO) {
        Role roleById = tempRoleDB.findRoleById(rolePermissionRequestDTO.getRoleId());
        if (roleById == null) {
            RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
            responseDTO.setStatus(ResponseStatus.ROLE_RESULT_NOT_FOUND);
            return responseDTO;
        }
        Permission permissionById = tempRoleDB.findPermissionById(rolePermissionRequestDTO.getPermissionId());
        if (permissionById == null) {
            RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
            responseDTO.setStatus(ResponseStatus.PERMISSION_RESULT_NOT_FOUND);
            return responseDTO;
        }
        return RolePermissionMapper.toPermissionResponseDTO(tempRoleDB.assignPermission(roleById, permissionById), ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
