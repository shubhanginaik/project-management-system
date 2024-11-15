package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.mapper.RolePermissionMapper;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.infrastructure.RolePermissionRepoImpl;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

/**
 * Role-Permission entity service layer
 */
@Service
public class RolePermissionServiceImpl {

    private final RolePermissionRepoImpl rolePermissionRepo;

    public RolePermissionServiceImpl(RolePermissionRepoImpl rolePermissionRepo) {
        this.rolePermissionRepo = rolePermissionRepo;
    }

    /**
     * assign permission to role
     * @param rolePermissionRequestDTO
     * @return
     */
    public RolePermissionResponseDTO assignPermissionToRole(@Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        if (rolePermissionRequestDTO.getRoleId() == null) {
            System.out.println("Role ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (rolePermissionRequestDTO.getPermissionId() == null) {
            System.out.println("Permission ID is null, cannot proceed with search.");
            return RolePermissionMapper.toPermissionResponseDTO(new RolePermission(), ResponseStatus.PERMISSION_ID_NOT_FOUND);
        }
        return this.rolePermissionRepo.createRolePermission(rolePermissionRequestDTO);
    }
}
