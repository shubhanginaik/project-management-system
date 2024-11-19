package fs19.java.backend.infrastructure.tempMemory;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Use as temporary  DB until JPA configuration
 */
@Component
public class RoleInMemoryDB {

    private final List<Role> existing_roles;
    private final List<Permission> existing_permission;
    private final List<RolePermission> existing_role_permission;

        existing_role_permission = new ArrayList<>();
        existing_roles = new ArrayList<>();
        existing_roles.add(new Role(UUID.randomUUID(), "DEV", new Date().toInstant().atZone(ZoneId.systemDefault())));
        existing_roles.add(new Role(UUID.randomUUID(), "QA", new Date().toInstant().atZone(ZoneId.systemDefault())));
        existing_roles.add(new Role(UUID.randomUUID(), "PM", new Date().toInstant().atZone(ZoneId.systemDefault())));

        existing_permission.add(new Permission(UUID.randomUUID(), "READ_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "WRITE_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "ADMIN_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "VIEW_ACCESS"));

    }

        existing_roles.add(myRole);
        return myRole;
    }

    public Permission createPermission(PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission = new Permission(UUID.randomUUID(), permissionRequestDTO.getName());
        existing_permission.add(myPermission);
        return myPermission;
    }

        Role myRole = null;
        // Find the role based on ID
        Optional<Role> roleOptional = existing_roles.stream()
                .filter(role1 -> role1.getId().toString().equalsIgnoreCase(roleId.toString()))
                .findFirst();

        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
            // Update the role properties
            existing_roles.remove(myRole);
            myRole.setName(role.getName());
            existing_roles.add(myRole);
        } else {
            System.out.println("Role with ID " + roleId + " not found in existing_roles.");
        }
        return myRole;
    }

    public Permission updatePermission(UUID permissionId, PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission = null;
        Optional<Permission> permissionOptional = existing_permission.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(permissionId.toString()))
                .findFirst();

        if (permissionOptional.isPresent()) {
            myPermission = permissionOptional.get();
            existing_permission.remove(myPermission);
            myPermission.setName(permissionRequestDTO.getName());
            existing_permission.add(myPermission);
        } else {
        }
        return myPermission;
    }


    public Role deleteRole(UUID roleId) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream()
                .filter(role1 -> role1.getId().toString().equalsIgnoreCase(roleId.toString()))
                .findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
            existing_roles.remove(myRole);
        } else {
            System.out.println("Role with ID " + roleId.toString() + " not found in existing Permission.");
        }
        return myRole;
    }

    public Permission deletePermission(UUID permissionId) {
        Permission myPermission = null;
        Optional<Permission> permissionOptional = existing_permission.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(permissionId.toString()))
                .findFirst();
        if (permissionOptional.isPresent()) {
            myPermission = permissionOptional.get();
            existing_permission.remove(myPermission);
        } else {
            System.out.println("Permission with ID " + permissionId + " not found in existing Permission.");
        }
        return myPermission;
    }


    public List<Role> findAllRoles() {
        return existing_roles;
    }

        return existing_permission;
    }


    public Role findRoleByName(@NotNull String name) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream().filter(role1 -> role1.getName().equalsIgnoreCase(name)).findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
        }
        return myRole;
    }

    public Role findRoleById(UUID id) {
        Role myRole = null;
        Optional<Role> roleOptional = existing_roles.stream().filter(role1 -> role1.getId().toString().equalsIgnoreCase(id.toString())).findFirst();
        if (roleOptional.isPresent()) {
            myRole = roleOptional.get();
        }
        return myRole;
    }

    public Permission findPermissionById(UUID id) {
        Permission permission = null;
        Optional<Permission> permissionOptional = existing_permission.stream().filter(p -> p.getId().toString().equalsIgnoreCase(id.toString())).findFirst();
        if (permissionOptional.isPresent()) {
            permission = permissionOptional.get();
        }
        return permission;
    }


    public Permission findPermissionByName(@NotNull String name) {
        Permission permission = null;
        Optional<Permission> permissionOptional = existing_permission.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
        if (permissionOptional.isPresent()) {
            permission = permissionOptional.get();
        }
        return permission;
    }


    public RolePermission assignPermission(Role roleById, Permission permissionById) {
        RolePermission myRolePermission = new RolePermission(UUID.randomUUID(), roleById, permissionById);
        existing_role_permission.add(myRolePermission);
        return myRolePermission;
    }
}
