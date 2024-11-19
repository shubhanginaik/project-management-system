package fs19.java.backend.application.dto.workspace_users;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUsersDTO {
  private UUID id;
  private UUID roleId;
  private UUID workspaceId;
  private UUID userId;

}
