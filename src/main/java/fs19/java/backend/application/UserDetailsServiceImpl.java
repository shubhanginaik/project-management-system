package fs19.java.backend.application;

import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepoCustom;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
     List<GrantedAuthority> authorities = userWithPermissionsDTO.get().getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
            .collect(Collectors.toList());

        // Build the UserDetails object
        return org.springframework.security.core.userdetails.User
            .builder()
            .username(userWithPermissionsDTO.get().getUserName())
            .password(userWithPermissionsDTO.get().getPassword())
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
    }
}
