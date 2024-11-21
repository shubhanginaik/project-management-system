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
    public RolePermissionResponseDTO create(@Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        if (rolePermissionRequestDTO.getRoleId() == null) {
            System.out.println("Role Id is null, cannot proceed with create.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (rolePermissionRequestDTO.getPermissionId() == null) {
            System.out.println("Permission Id is null, cannot proceed with create.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        RolePermission resultIfExist = rolePermissionRepo.existsById(rolePermissionRequestDTO.getRoleId(), rolePermissionRequestDTO.getPermissionId());
        if (resultIfExist == null) {
            return rolePermissionRepo.save(rolePermissionRequestDTO);
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
    public RolePermissionResponseDTO update(UUID rolePermissionId, @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        if (rolePermissionId == null) {
            System.out.println("Role-Permission Id is null, cannot proceed with update.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        }
        else {
            return rolePermissionRepo.update(rolePermissionId, rolePermissionRequestDTO);
        }
    }


    /**
     * Get all permission records
     *
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> findAll() {
        return rolePermissionRepo.findAll();
    }

    /**
     * Get role permission by record id
     *
     * @param permissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO findByPermissionId(UUID permissionId) {
        if (permissionId == null) {
            System.out.println("Role-Permission ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        }
        return rolePermissionRepo.findById(permissionId);
    }

    /**
     * load permission by record id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> findByRoleId(UUID roleId) {
        if (roleId == null) {
            System.out.println(" Role ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTOs(new ArrayList<>(), ResponseStatus.ROLE_ID_NOT_FOUND);
        }
        return rolePermissionRepo.findByRoleId(roleId);
    }

    /**
     * load role-permission by id
     *
     * @param rolePermissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO delete(UUID rolePermissionId) {
        if (rolePermissionId.toString() == null) {
            System.out.println("Role-Permission ID is null, cannot proceed with delete.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_PERMISSION_ID_NOT_FOUND);
        }
        return rolePermissionRepo.delete(rolePermissionId);
    }

    /**
     * Load role-permission by permission id
     *
     * @param permissionId
     * @return
     */
    @Override
    public List<RolePermissionResponseDTO> findById(UUID permissionId) {
        if (permissionId == null) {
            System.out.println(" Permission ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTOs(new ArrayList<>(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        return rolePermissionRepo.findByPermissionId(permissionId);
    }

    /**
     * Check already record is exist in a given system
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @Override
    public RolePermissionResponseDTO findByRoleIdAndPermissionId(UUID roleId, UUID permissionId) {
        if (roleId == null) {
            System.out.println("Role Id is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (permissionId == null) {
            System.out.println("Permission Id is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        RolePermission resultIfExist = rolePermissionRepo.existsById(roleId, permissionId);
        if (resultIfExist == null) {
            System.out.println(" Invalid Information, records not found.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.INVALID_INFORMATION_ROLE_PERMISSION_DETAILS_NOT_FOUND);
        }
        return RolePermissionMapper.toPermissionResponseDTO(resultIfExist, ResponseStatus.SUCCESSFULLY_FOUND);

    }
}
