package fs19.java.backend.infrastructure.JpaRepositories;

import fs19.java.backend.domain.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, UUID>  {

}
