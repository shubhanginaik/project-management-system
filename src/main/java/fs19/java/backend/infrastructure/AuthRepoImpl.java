package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthRepoImpl {

    private final RoleJpaRepo roleJpaRepo;
    private final RolePermissionJpaRepo rolePermissionJpaRepo;
    private final PermissionJpaRepo permissionJpaRepo;
    private final WorkspaceUserJpaRepo workspaceUserJpaRepo;
    private final UserJpaRepo userRepo;
    private final UserJpaRepoCustom userJpaRepoCustom;


    public AuthRepoImpl(RoleJpaRepo roleJpaRepo, RolePermissionJpaRepo rolePermissionJpaRepo, PermissionJpaRepo permissionJpaRepo, WorkspaceUserJpaRepo workspaceUserJpaRepo, UserJpaRepo userRepo, UserJpaRepoCustom userJpaRepoCustom) {
        this.roleJpaRepo = roleJpaRepo;
        this.rolePermissionJpaRepo = rolePermissionJpaRepo;
        this.permissionJpaRepo = permissionJpaRepo;
        this.workspaceUserJpaRepo = workspaceUserJpaRepo;
        this.userRepo = userRepo;
        this.userJpaRepoCustom = userJpaRepoCustom;
    }

    public Optional<User> findByEmail(String username) {
        return userRepo.findByEmail(username);
    }

    public User saveUser(User user) {
        userRepo.save(user);
        return user;
    }

    public List<UUID> findLinkWorkspaceIds(UUID id) {
        return userJpaRepoCustom.findLinkWorkspaceIds(id);
    }
}
