package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.application.mapper.RoleMapper;
import fs19.java.backend.infrastructure.RoleRepoImpl;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl {

    private final RoleRepoImpl roleRepo;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepoImpl roleRepo) {
        this.roleRepo = roleRepo;
        this.roleMapper = new RoleMapper();
    }

    public RoleResponseDTO createRole(@Valid RoleRequestDTO roleRequestDTO) {
        return this.roleMapper.toRoleResponseDTO(this.roleRepo.createRole(roleRequestDTO), ResponseStatus.SUCCESSFULLY_CREATED);
    }

    public RoleResponseDTO updateRole(@Valid RoleRequestDTO roleRequestDTO) {
        return this.roleMapper.toRoleResponseDTO(this.roleRepo.updateRole(roleRequestDTO), ResponseStatus.SUCCESSFULLY_UPDATED);
    }

    public RoleResponseDTO deleteRole(@Valid RoleRequestDTO roleRequestDTO) {
        return this.roleMapper.toRoleResponseDTO(this.roleRepo.deleteRole(roleRequestDTO), ResponseStatus.SUCCESSFULLY_DELETED);
    }

    public List<RoleResponseDTO> getRoles() {
        return this.roleMapper.toRoleResponseDTOs(this.roleRepo.getRoles(), ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
