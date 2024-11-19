package fs19.java.backend.domain.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUsers {
  private UUID id;
  private UUID userId;
  private UUID roleId;
  private UUID workspaceId;
}
