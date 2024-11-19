package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.domain.abstraction.RoleRepository;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.tempMemory.RoleInMemoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Role Repo implementation
 */
@Repository
public class RoleRepoImpl implements RoleRepository {

    @Autowired
    private RoleInMemoryDB tempRoleDB;

    /**
     * Create a new role according to user specified details
     *
     * @param role    RoleRequestDTO
     * @param company
     * @return Role
     */
    @Override
    public Role createRole(RoleRequestDTO role, Company company) {
        return tempRoleDB.createRole(role, company);
    }

    /**
     * Update role according to user specified data
     *
     * @param roleId
     * @param role
     * @param company
     * @return
     */
    @Override
    public Role updateRole(UUID roleId, RoleRequestDTO role, Company company) {
        return tempRoleDB.updateRole(roleId, role,company);
    }

    @Override
    public Role deleteRole(UUID roleId) {
        return tempRoleDB.deleteRole(roleId);
    }

    /**
     * Return all roles
     *
     * @return
     */
    @Override
    public List<Role> getRoles() {
        return tempRoleDB.findAllRoles();
    }

    @Override
    public Role getRoleByName(String roleName) {
        return tempRoleDB.findRoleByName(roleName);
    }

    /**
     * Get role By Id
     *
     * @param roleId
     * @return
     */
    @Override
    public Role getRoleById(UUID roleId) {
        return tempRoleDB.findRoleById(roleId);
    }
}
