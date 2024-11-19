package fs19.java.backend.application;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.application.mapper.RoleMapper;
import fs19.java.backend.application.service.RoleService;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.CompanyRepoImpl;
import fs19.java.backend.infrastructure.RoleRepoImpl;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Role entity service layer
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepoImpl roleRepo;
    private final CompanyRepoImpl companyRepo;

    public RoleServiceImpl(RoleRepoImpl roleRepo, CompanyRepoImpl companyRepo) {
        this.roleRepo = roleRepo;
        this.companyRepo = companyRepo;
    }

    /**
     * Create a new role according to user data
     *
     * @param roleRequestDTO
     * @return
     */
    @Override
    public RoleResponseDTO createRole(@Valid RoleRequestDTO roleRequestDTO) {
        Role myRole;
        if (roleRequestDTO.getName().isEmpty()) { // expected valid name only and that validation is enough
            System.out.println("Role Name from DTO is null, cannot proceed with Role creation.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_NAME_NOT_FOUND);
        }
        if (roleRequestDTO.getCompanyId() == null) { // expected valid name only and that validation is enough
            System.out.println("Role Name from DTO is null, cannot proceed with Role creation.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.COMPANY_ID_NOT_FOUND);
        }
        Optional<Company> companyOptional = companyRepo.findById(roleRequestDTO.getCompanyId());
        if (companyOptional.isPresent()) {
            if (this.roleRepo.getRoleByName(roleRequestDTO.getName()) == null) {
                myRole = this.roleRepo.createRole(roleRequestDTO, companyOptional.get());
                if (myRole == null) {
                    return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
                }
                System.out.println("Role-Created successfully");
                return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_CREATED);
            } else {
                System.out.println("Role-Already Found");
                return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.RECORD_ALREADY_CREATED);
            }
        } else {
            System.out.println("Company-Name, cannot proceed with Role creation.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.COMPANY_NAME_NOT_FOUND);
        }


    }

    /**
     * Update a role according to user data
     *
     * @param roleId
     * @param roleRequestDTO
     * @return
     */
    @Override
    public RoleResponseDTO updateRole(UUID roleId, @Valid RoleRequestDTO roleRequestDTO) {
        Role myRole;
        if (roleId == null) {
            System.out.println("Role ID is null, cannot proceed with update.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_ID_NOT_FOUND);
        } else if (roleRequestDTO.getName().isEmpty()) {
            System.out.println("Define Role name from DTO is null, cannot proceed with update.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_NAME_NOT_FOUND);
        } else if (roleRequestDTO.getCompanyId() == null) { // expected valid name only and that validation is enough
            System.out.println("Role Name from DTO is null, cannot proceed with Role creation.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.COMPANY_ID_NOT_FOUND);
        }

        Optional<Company> companyOptional = companyRepo.findById(roleRequestDTO.getCompanyId());
        if (companyOptional.isPresent()) {
            if (this.roleRepo.getRoleByName(roleRequestDTO.getName()) == null) {
                myRole = this.roleRepo.updateRole(roleId, roleRequestDTO,companyOptional.get());
                if (myRole == null) {
                    return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
                }
                System.out.println("Role-Updated successfully");
                return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_UPDATED);
            } else {
                System.out.println("Role-Already Found");
                return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.RECORD_ALREADY_CREATED);
            }
        } else {
            System.out.println("Company-Name, cannot proceed with Role creation.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.COMPANY_NAME_NOT_FOUND);
        }
    }

    /**
     * Delete role according to user details
     *
     * @param roleId UUID
     * @return RoleResponseDTO
     */
    @Override
    public RoleResponseDTO deleteRole(UUID roleId) {
        if (roleId == null) {
            System.out.println("Role ID is null, cannot proceed with delete.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_ID_NOT_FOUND);
        }
        Role myRole = this.roleRepo.deleteRole(roleId);
        if (myRole == null) {
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
        }
        System.out.println("Role-Deleted successfully");
        return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_DELETED);
    }

    /**
     * Return all roles
     *
     * @return
     */
    @Override
    public List<RoleResponseDTO> getRoles() {
        return RoleMapper.toRoleResponseDTOs(this.roleRepo.getRoles(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Search Role by Id
     *
     * @param roleId
     * @return
     */
    @Override
    public RoleResponseDTO getRoleById(UUID roleId) {
        if (roleId == null) {
            System.out.println("Role ID is null, cannot proceed with search.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_ID_NOT_FOUND);
        }
        Role myRole = this.roleRepo.getRoleById(roleId);
        if (myRole == null) {
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
        }
        return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_FOUND);
    }

    /**
     * Search Role By Name
     *
     * @param name
     * @return
     */
    @Override
    public RoleResponseDTO getRoleByName(String name) {
        if (name.isEmpty()) {
            System.out.println("Role Name is null, cannot proceed with search.");
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.ROLE_NAME_NOT_FOUND);
        }
        Role myRole = this.roleRepo.getRoleByName(name);
        if (myRole == null) {
            return RoleMapper.toRoleResponseDTO(new Role(), ResponseStatus.INVALID_INFORMATION_ROLE_DETAILS_NOT_FOUND);
        }
        return RoleMapper.toRoleResponseDTO(myRole, ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
