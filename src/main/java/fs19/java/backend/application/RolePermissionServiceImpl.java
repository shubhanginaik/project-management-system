package fs19.java.backend.application;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.mapper.RolePermissionMapper;
import fs19.java.backend.application.service.RolePermissionService;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.infrastructure.RolePermissionRepoImpl;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Role-Permission entity service layer
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepoImpl rolePermissionRepo;

    public RolePermissionServiceImpl(RolePermissionRepoImpl rolePermissionRepo) {
        this.rolePermissionRepo = rolePermissionRepo;
    }

    /**
     * Assign permission to role
     *
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO assignPermissionToRole(@Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        if (rolePermissionRequestDTO.getRoleId() == null) {
            System.out.println("Role Id is null, cannot proceed with create.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (rolePermissionRequestDTO.getPermissionId() == null) {
            System.out.println("Permission Id is null, cannot proceed with create.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        RolePermission resultIfExist = rolePermissionRepo.getResultIfExist(rolePermissionRequestDTO.getRoleId(), rolePermissionRequestDTO.getPermissionId());
        if (resultIfExist == null) {
            return rolePermissionRepo.createRolePermission(rolePermissionRequestDTO);
        }
        System.out.println("Record Already created,Please use the existing information (Role permission id :" + resultIfExist.getId() + ")");
        return RolePermissionMapper.toPermissionResponseDTO(resultIfExist, ResponseStatus.ROLE_PERMISSION_ID_RECORD_ALREADY_EXIST);
    }

    /**
     * Update role permission record
     *
     * @param rolePermissionId
     * @param rolePermissionRequestDTO
     * @return
     */
    @Override
    public RolePermissionResponseDTO updateRolePermission(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        if (rolePermissionId == null) {
            System.out.println("Role-Permission Id is null, cannot proceed with update.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        } else {
            RolePermissionResponseDTO rolePermissionById = rolePermissionRepo.getRolePermissionById(rolePermissionId);
            if (rolePermissionById.getId() == null) {
                System.out.println("Record not found,Can't execute the update request (Role permission id :" + rolePermissionId + ")");
                return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.INVALID_INFORMATION_ROLE_PERMISSION_DETAILS_NOT_FOUND);

            }
            RolePermission resultIfExist = rolePermissionRepo.getResultIfExist(rolePermissionRequestDTO.getRoleId(), rolePermissionRequestDTO.getPermissionId());
            if (resultIfExist == null) {
                return getUpdatedRolePermissionResponseDTO(rolePermissionId, rolePermissionRequestDTO);
            }
            System.out.println("Record Already created,Can't execute the update request (Role permission id :" + resultIfExist.getId() + ")");
            return RolePermissionMapper.toPermissionResponseDTO(resultIfExist, ResponseStatus.ROLE_PERMISSION_ID_RECORD_ALREADY_EXIST);
        }
    }


    /**
     * Get all permission records
     *
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> getRolePermissions() {
        return rolePermissionRepo.getRolePermissions();
    }

    /**
     * Get role permission by record Id
     *
     * @param rolePermissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO getRolePermissionById(UUID rolePermissionId) {
        if (rolePermissionId.toString() == null) {
            System.out.println("Role-Permission ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        }
        return rolePermissionRepo.getRolePermissionById(rolePermissionId);
    }

    /**
     * load permission by record Id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> getPermissionByRoleId(UUID roleId) {
        if (roleId.toString() == null) {
            System.out.println(" Role ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTOs(new ArrayList<>(), ResponseStatus.ROLE_ID_NOT_FOUND);
        }
        return rolePermissionRepo.getPermissionByRoleId(roleId);
    }

    /**
     * load role-permission by Id
     *
     * @param rolePermissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO deleteRolePermission(UUID rolePermissionId) {
        if (rolePermissionId.toString() == null) {
            System.out.println("Role-Permission ID is null, cannot proceed with delete.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        }
        return rolePermissionRepo.deleteRolePermission(rolePermissionId);
    }

    /**
     * Load role-permission by permission Id
     *
     * @param permissionId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> getRolesByPermissionByPermissionId(UUID permissionId) {
        if (permissionId.toString() == null) {
            System.out.println(" Permission ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTOs(new ArrayList<>(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        return rolePermissionRepo.getRolesByPermissionId(permissionId);
    }

    /**
     * Check already record is exist in given system
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO getExistingRecord(UUID roleId, UUID permissionId) {
        if (roleId == null) {
            System.out.println("Role Id is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (permissionId == null) {
            System.out.println("Permission Id is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        RolePermission resultIfExist = rolePermissionRepo.getResultIfExist(roleId, permissionId);
        if (resultIfExist == null) {
            System.out.println(" Invalid Information, records not found.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.INVALID_INFORMATION_ROLE_PERMISSION_DETAILS_NOT_FOUND);
        }
        return RolePermissionMapper.toPermissionResponseDTO(resultIfExist, ResponseStatus.SUCCESSFULLY_FOUND);

    }

    /**
     * updateRole-permission
     *
     * @param rolePermissionId
     * @param rolePermissionRequestDTO
     * @return
     */
    private RolePermissionResponseDTO getUpdatedRolePermissionResponseDTO(UUID rolePermissionId, RolePermissionRequestDTO rolePermissionRequestDTO) {
        if (rolePermissionRequestDTO.getRoleId() == null) {
            System.out.println("Role Id is null, cannot proceed with update.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (rolePermissionRequestDTO.getPermissionId() == null) {
            System.out.println("Permission Id is null, cannot proceed with update.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        } else {
            return rolePermissionRepo.updateRolePermission(rolePermissionId, rolePermissionRequestDTO);
        }
    }
}
