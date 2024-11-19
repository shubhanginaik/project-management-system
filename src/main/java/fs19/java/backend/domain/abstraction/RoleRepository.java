package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;

import java.util.List;
import java.util.UUID;

/**
 * Define the role repository
 */
public interface RoleRepository {

    Role createRole(RoleRequestDTO role, Company company);

    Role updateRole(UUID roleId, RoleRequestDTO role, Company company);

    Role deleteRole(UUID roleId);

    List<Role> getRoles();

    Role getRoleByName(String roleName);

    Role getRoleById(UUID roleId);

}
