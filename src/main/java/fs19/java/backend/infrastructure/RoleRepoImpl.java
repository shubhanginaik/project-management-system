package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.domain.abstraction.RoleRepository;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.tempMemory.RoleInMemoryDB;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepoImpl implements RoleRepository {
    private final RoleInMemoryDB tempRoleDB;

    public RoleRepoImpl(RoleInMemoryDB tempRoleDB) {
        this.tempRoleDB = tempRoleDB;
    }

    @Override
    public Role createRole(RoleRequestDTO role) {
        return tempRoleDB.saveRole(role);
    }

    @Override
    public Role updateRole(RoleRequestDTO role) {
        return tempRoleDB.updateRole(role);
    }

    @Override
    public Role deleteRole(RoleRequestDTO role) {
        return tempRoleDB.deleteRole(role);
    }

    @Override
    public List<Role> getRoles() {
        return tempRoleDB.findAllRoles();
    }

    @Override
    public Role getRoleByName(RoleRequestDTO role) {
        return tempRoleDB.findRoleByName(role.getName());
    }

    @Override
    public Role getRoleById(RoleRequestDTO role) {
        return tempRoleDB.getRoleById(role.getId());
    }
}
