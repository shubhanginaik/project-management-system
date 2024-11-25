// UserJpaRepoCustomImpl.java
package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.user.PermissionDTO;
import fs19.java.backend.application.dto.user.UserPermissionsDTO;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepoCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserJpaRepoCustomImpl implements UserJpaRepoCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<UserPermissionsDTO> findPermissionsByUserEmail(String email) {
    Query query = entityManager.createNativeQuery(
            "SELECT u.email as userName,u.password as password, " +
                "p.id as permissionId, p.name as permissionName " +
                "FROM users u " +
                "LEFT JOIN workspace_user wu ON wu.user_id = u.id " +
                "LEFT JOIN role_permission r ON wu.role_id = r.role_id " +
                "LEFT JOIN permission p ON r.permission_id = p.id " +
                "WHERE u.email = :email")
        .setParameter("email", email);

    List<Object[]> results = query.getResultList();

    if (results.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapNativeResult(results));
  }

  private UserPermissionsDTO mapNativeResult(List<Object[]> results) {
    UserPermissionsDTO userPermissionsDTO = new UserPermissionsDTO();
    List<PermissionDTO> permissions = new ArrayList<>();

    for (Object[] row : results) {
      if (userPermissionsDTO.getUserName() == null) {
        userPermissionsDTO.setUserName((String) row[0]);
        userPermissionsDTO.setPassword((String) row[1]);
      }
      if (row[2] != null) {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setPermissionId(((UUID) row[2]));
        permissionDTO.setPermissionName((String) row[3]);
        permissions.add(permissionDTO);
      }
    }

    userPermissionsDTO.setPermissions(permissions);
    return userPermissionsDTO;
  }
}