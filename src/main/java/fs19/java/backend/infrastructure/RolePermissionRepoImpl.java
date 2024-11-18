package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.mapper.RolePermissionMapper;
import fs19.java.backend.domain.abstraction.RolePermissionRepository;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.infrastructure.tempMemory.RoleInMemoryDB;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Role-Permission Repo implementation
 */
@Repository
public class RolePermissionRepoImpl implements RolePermissionRepository {

    @Autowired
    private RoleInMemoryDB tempRoleDB;

    /**
     * create role permission by using user info
     *
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

    /**
     * Responsible to search all role permissions
     *
     * @return List<RolePermissionResponseDTO>
     */
    @Override
    public List<RolePermissionResponseDTO> getRolePermissions() {
        return RolePermissionMapper.toPermissionResponseDTOs(tempRoleDB.findAllRolePermissions(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * search role-permission by id
     *
     * @param id
     * @return
     */
    @Override
    public RolePermissionResponseDTO getRolePermissionById(UUID id) {
        if (id == null) {
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        } else {
            RolePermission rolePermission = tempRoleDB.findRolePermissionById(id);
            if (rolePermission == null) {
                RolePermissionResponseDTO responseDTO = new RolePermissionResponseDTO();
                responseDTO.setStatus(ResponseStatus.ROLE_PERMISSION_RESULT_NOT_FOUND);
                return responseDTO;
            }
            return RolePermissionMapper.toPermissionResponseDTO(rolePermission, ResponseStatus.SUCCESSFULLY_FOUND);

        }
    }

    /**
     * Update role permission By Id
     *
     * @param rolePermissionId
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO updateRolePermission(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermission resultIfExist = getResultIfExist(rolePermissionRequestDTO.getRoleId(), rolePermissionRequestDTO.getPermissionId());
        if (resultIfExist == null) {
            RolePermissionResponseDTO responseDTO = validateWithExistingRecords(rolePermissionRequestDTO);
            if (responseDTO != null) return responseDTO;
            return RolePermissionMapper.toPermissionResponseDTO(tempRoleDB.updateRolePermission(rolePermissionId, rolePermissionRequestDTO), ResponseStatus.SUCCESSFULLY_UPDATED);
        } else {
            return RolePermissionMapper.toPermissionResponseDTO(resultIfExist, ResponseStatus.ROLE_PERMISSION_ID_RECORD_ALREADY_EXIST);
        }
    }

    /**
     * Validate existing records are available in the DB
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO validateWithExistingRecords(RolePermissionRequestDTO rolePermissionRequestDTO) {
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
        return null;
    }

    /**
     * Delete role-permission by Id
     *
     * @param rolePermissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO deleteRolePermission(UUID rolePermissionId) {
        RolePermission rolePermissionById = tempRoleDB.findRolePermissionById(rolePermissionId);
        if (rolePermissionById == null) {
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_RESULT_NOT_FOUND);
        } else {
            return RolePermissionMapper.toPermissionResponseDTO(tempRoleDB.deleteRolePermission(rolePermissionId), ResponseStatus.SUCCESSFULLY_DELETED);
        }
    }

    /**
     * Load roles according to permission Id
     *
     * @param permissionId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> getRolesByPermissionId(UUID permissionId) {
        return RolePermissionMapper.toPermissionResponseDTOs(tempRoleDB.finaAllRolesByPermissionId(permissionId), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Load permissions by role Id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> getPermissionByRoleId(UUID roleId) {
        return RolePermissionMapper.toPermissionResponseDTOs(tempRoleDB.findAllPermissionByRoleId(roleId), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Check role-permission record already exists
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @Override
    public RolePermission getResultIfExist(UUID roleId, UUID permissionId) {
        return tempRoleDB.isAlreadyAssignedPermission(roleId, permissionId);
    }
}
