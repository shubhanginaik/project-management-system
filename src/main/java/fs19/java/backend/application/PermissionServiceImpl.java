package fs19.java.backend.application;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import fs19.java.backend.application.mapper.PermissionMapper;
import fs19.java.backend.application.service.PermissionService;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.infrastructure.PermissionRepoImpl;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Permission entity service layer
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepoImpl permissionRepo;

    public PermissionServiceImpl(PermissionRepoImpl PermissionRepoImpl) {
        this.permissionRepo = PermissionRepoImpl;
    }

    /**
     * Create a new permission
     *
     * @param permissionRequestDTO
     * @return
     */
    @Override
    public PermissionResponseDTO createPermission(@Valid PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission;
        if (permissionRequestDTO.getName().isEmpty()) { // expected valid name only and that validation is enough
            System.out.println("Permission Name from DTO is null, cannot proceed with Permission creation.");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_NAME_NOT_FOUND);
        }
        if (this.permissionRepo.getPermissionByName(permissionRequestDTO.getName()) == null) {
            myPermission = this.permissionRepo.createPermission(permissionRequestDTO);
            if (myPermission == null) {
                return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
            }
            System.out.println("Permission-Created successfully");
            return PermissionMapper.toPermissionResponseDTO(myPermission, ResponseStatus.SUCCESSFULLY_CREATED);
        } else {
            System.out.println("RoleModel-Already Found");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.RECORD_ALREADY_CREATED);
        }
    }


    /**
     * Update a Permission according to user data
     *
     * @param permissionId
     * @param permissionRequestDTO
     * @return
     */
    @Override
    public PermissionResponseDTO updatePermission(UUID permissionId, @Valid PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission;
        if (permissionId == null) {
            System.out.println("Permission ID is null, cannot proceed with update.");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        } else if (permissionRequestDTO.getName().isEmpty()) {
            System.out.println("Define Permission name from DTO is null, cannot proceed with update.");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        if (this.permissionRepo.getPermissionByName(permissionRequestDTO.getName()) == null) {
            myPermission = this.permissionRepo.updatePermission(permissionId, permissionRequestDTO);
            if (myPermission == null) {
                return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
            }
            System.out.println("Permission-Updated successfully");
            return PermissionMapper.toPermissionResponseDTO(myPermission, ResponseStatus.SUCCESSFULLY_UPDATED);
        } else {
            System.out.println("Permission-Already Found");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.RECORD_ALREADY_CREATED);
        }
    }

    /**
     * Delete Permission according to user details
     *
     * @param permissionId UUID
     * @return PermissionResponseDTO
     */
    @Override
    public PermissionResponseDTO deletePermission(UUID permissionId) {
        if (permissionId == null) {
            System.out.println("Permission ID is null, cannot proceed with delete.");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        Permission myPermission = this.permissionRepo.deletePermission(permissionId);
        if (myPermission == null) {
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
        }
        System.out.println("Permission-Deleted successfully");
        return PermissionMapper.toPermissionResponseDTO(myPermission, ResponseStatus.SUCCESSFULLY_DELETED);
    }

    /**
     * load all permissions
     *
     * @return List<PermissionResponseDTO>
     */
    @Override
    public List<PermissionResponseDTO> getPermissions() {
        return PermissionMapper.toRoleResponseDTOs(this.permissionRepo.getPermissions(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Search Permission by id
     *
     * @param permissionId
     * @return
     */
    @Override
    public PermissionResponseDTO getPermissionById(UUID permissionId) {
        if (permissionId == null) {
            System.out.println("Permission Id is null, cannot proceed with search.");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        Permission myPermission = this.permissionRepo.getPermissionById(permissionId);
        if (myPermission == null) {
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
        }
        return PermissionMapper.toPermissionResponseDTO(myPermission, ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Search Permission By Name
     *
     * @param name
     * @return
     */
    @Override
    public PermissionResponseDTO getPermissionByName(String name) {
        if (name.isEmpty()) {
            System.out.println("Permission Name is null, cannot proceed with search.");
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.PERMISSION_NAME_NOT_FOUND);
        }
        Permission permission = this.permissionRepo.getPermissionByName(name);
        if (permission == null) {
            return PermissionMapper.toPermissionResponseDTO(new Permission(), ResponseStatus.INVALID_INFORMATION_PERMISSION_DETAILS_NOT_FOUND);
        }
        return PermissionMapper.toPermissionResponseDTO(permission, ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
