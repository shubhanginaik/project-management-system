package fs19.java.backend.infrastructure.tempMemory;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.domain.entity.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleInMemoryDB {

    private final List<Role> existing_roles;

    public RoleInMemoryDB() {
        existing_roles = new ArrayList<>();
        existing_roles.add(new Role(UUID.randomUUID(), "DEV", new Date()));
        existing_roles.add(new Role(UUID.randomUUID(), "QA", new Date()));
        existing_roles.add(new Role(UUID.randomUUID(), "PM", new Date()));
    }

    public Role saveRole(RoleRequestDTO role) {
        Role myRole = new Role(UUID.randomUUID(), role.getName(), role.getCreated_date());
        existing_roles.add(myRole);
        return myRole;
    }

    public Role updateRole(RoleRequestDTO role) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream().filter(role1 -> role1.getId() == role.getId()).findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
            existing_roles.remove(myRole);
            myRole.setName(role.getName());
            myRole.setCreated_date(role.getCreated_date());
            existing_roles.add(myRole);
        }
        return myRole;
    }

    public Role deleteRole(RoleRequestDTO role) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream().filter(role1 -> role1.getId() == role.getId()).findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
            existing_roles.remove(myRole);
        }
        return myRole;
    }


    public List<Role> findAllRoles() {
        return existing_roles;
    }


    public Role findRoleByName(@NotNull String name) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream().filter(role1 -> role1.getName().equalsIgnoreCase(name)).findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
        }
        return myRole;
    }

    public Role getRoleById(UUID id) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream().filter(role1 -> role1.getId() == id).findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
        }
        return myRole;
    }
}
