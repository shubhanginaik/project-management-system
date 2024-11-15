package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.domain.entity.Role;

import java.util.List;

public interface RoleRepository {

    Role createRole(RoleRequestDTO role);

    Role updateRole(RoleRequestDTO role);

    Role deleteRole(RoleRequestDTO role);

    List<Role> getRoles();

    Role getRoleByName(RoleRequestDTO role);

    Role getRoleById(RoleRequestDTO role);

}
