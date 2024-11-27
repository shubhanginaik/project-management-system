package fs19.java.backend.application;

import fs19.java.backend.application.dto.user.UserPermissionsDTO;
import fs19.java.backend.config.SecurityRole;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepoCustom;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserJpaRepoCustom userJpaRepoCustom;

    public UserDetailsServiceImpl(UserJpaRepoCustom userJpaRepoCustom) {
        this.userJpaRepoCustom = userJpaRepoCustom;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userWithPermissionsDTO = userJpaRepoCustom.findPermissionsByUserEmail(username);
        if (userWithPermissionsDTO.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Extract permissions as authorities
        return getUserDetails(userWithPermissionsDTO.get());
    }

    public UserDetails loadUserByUserNameAndWorkspaceId(String username, UUID workspaceId) throws UsernameNotFoundException {
        var userWithPermissionsDTO = userJpaRepoCustom.findPermissionsByUserEmailAndWorkspaceId(username, workspaceId);
        if (userWithPermissionsDTO.isEmpty()) {
            // todo: workspaceID
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return getUserDetails(userWithPermissionsDTO.get());
    }

    private static UserDetails getUserDetails(UserPermissionsDTO userWithPermissionsDTO) {
        // Extract permissions as authorities
        List<GrantedAuthority> authorities = userWithPermissionsDTO.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
                .collect(Collectors.toList());

        // Build the UserDetails object
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(userWithPermissionsDTO.getUserName())
                .password(userWithPermissionsDTO.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public List<SecurityRole> findAllPermissions() {
        return this.userJpaRepoCustom.findAllPermissions();
    }

}
