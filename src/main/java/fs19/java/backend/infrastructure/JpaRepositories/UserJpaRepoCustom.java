package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.application.dto.user.UserPermissionsDTO;
import java.util.Optional;

public interface UserJpaRepoCustom {
  Optional<UserPermissionsDTO> findPermissionsByUserEmail(String email);
}
