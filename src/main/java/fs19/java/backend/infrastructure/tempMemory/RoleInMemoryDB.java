package fs19.java.backend.infrastructure.tempMemory;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.domain.entity.Permission;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.RolePermission;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.*;

/**
 * Use as temporary  DB until JPA configuration
 */
@Component
public class RoleInMemoryDB {

    private final List<Role> existing_roles;
    private final List<Permission> existing_permission;
    private final List<RolePermission> existing_role_permission;

    public RoleInMemoryDB(List<Permission> existingPermission) {
        existing_permission = existingPermission;
        existing_role_permission = new ArrayList<>();
        existing_roles = new ArrayList<>();
        existing_roles.add(new Role(UUID.randomUUID(), "DEV", new Date().toInstant().atZone(ZoneId.systemDefault())));
        existing_roles.add(new Role(UUID.randomUUID(), "QA", new Date().toInstant().atZone(ZoneId.systemDefault())));
        existing_roles.add(new Role(UUID.randomUUID(), "PM", new Date().toInstant().atZone(ZoneId.systemDefault())));

        existing_permission.add(new Permission(UUID.randomUUID(), "READ_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "WRITE_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "ADMIN_ACCESS"));
        existing_permission.add(new Permission(UUID.randomUUID(), "VIEW_ACCESS"));

        existing_role_permission.add(new RolePermission(UUID.randomUUID(), existingPermission.get(0).getId(), existingPermission.get(0).getId()));

    }

    public Role createRole(RoleRequestDTO roleRequestDTO) {
        Role myRole = new Role(UUID.randomUUID(), roleRequestDTO.getName(), DateAndTime.getDateAndTime());
        existing_roles.add(myRole);
        return myRole;
    }

    public Permission createPermission(PermissionRequestDTO permissionRequestDTO) {
        Permission myPermission = new Permission(UUID.randomUUID(), permissionRequestDTO.getName());
        existing_permission.add(myPermission);
        return myPermission;
    }

    public Role updateRole(UUID roleId, RoleRequestDTO role) {
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
            myRole.setCreated_date(DateAndTime.getDateAndTime());
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
            System.out.println("Permission with ID " + permissionId + " not found in existing_roles.");
        }
        return myPermission;
    }


    public RolePermission updateRolePermission(UUID rolePermissionId, RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermission myRolePermission = null;
        Optional<RolePermission> permissionOptional = existing_role_permission.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(rolePermissionId.toString()))
                .findFirst();

        if (permissionOptional.isPresent()) {
            myRolePermission = permissionOptional.get();
            existing_role_permission.remove(myRolePermission);
            myRolePermission.setPermissionId(rolePermissionRequestDTO.getPermissionId());
            myRolePermission.setRoleId(rolePermissionRequestDTO.getRoleId());
            existing_role_permission.add(myRolePermission);
        } else {
            System.out.println("Role-Permission with ID " + rolePermissionId + " not found in existing_roles.");
        }
        return myRolePermission;
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

    public List<Permission> finaAllPermissions() {
        return existing_permission;
    }

    public List<RolePermission> finaAllRolePermissions() {
        return existing_role_permission;
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

    public RolePermission findRolePermissionById(UUID id) {
        RolePermission myRolePermission = null;
        Optional<RolePermission> roleOptional = existing_role_permission.stream().filter(rolePermission -> rolePermission.getId().toString().equalsIgnoreCase(id.toString())).findFirst();
        if (roleOptional.isPresent()) {
            myRolePermission = roleOptional.get();
        }
        return myRolePermission;
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
        RolePermission myRolePermission = new RolePermission(UUID.randomUUID(), roleById.getId(), permissionById.getId());
        existing_role_permission.add(myRolePermission);
        return myRolePermission;
    }

    public RolePermission isAlreadyAssignedPermission(@NotNull UUID roleId, @NotNull UUID permissionId) {
        RolePermission myRolePermission = null;
        Optional<RolePermission> roleOptional = existing_role_permission.stream().filter(rolePermission -> (rolePermission.getRoleId().toString().equalsIgnoreCase(roleId.toString()) && rolePermission.getPermissionId().toString().equalsIgnoreCase(permissionId.toString()))).findFirst();
        if (roleOptional.isPresent()) {
            myRolePermission = roleOptional.get();
        }
        return myRolePermission;
    }

    public RolePermission deleteRolePermission(UUID rolePermissionId) {
        RolePermission myRolePermission = null;
        Optional<RolePermission> rolePermission = existing_role_permission.stream()
                .filter(p -> p.getId().toString().equalsIgnoreCase(rolePermissionId.toString()))
                .findFirst();
        if (rolePermission.isPresent()) {
            myRolePermission = rolePermission.get();
            existing_role_permission.remove(myRolePermission);
        } else {
            System.out.println("Role-Permission with ID " + rolePermissionId + " not found in existing Permission.");
        }
        return myRolePermission;
    }

    public List<RolePermission> finaAllRolesByPermissionId(UUID permissionId) {
        return existing_role_permission.stream()
                .filter(p -> p.getPermissionId().toString().equalsIgnoreCase(permissionId.toString())).toList();
    }

    public List<RolePermission> findAllPermissionByRoleId(UUID roleId) {
        return existing_role_permission.stream()
                .filter(p -> p.getRoleId().toString().equalsIgnoreCase(roleId.toString())).toList();
    }
}
