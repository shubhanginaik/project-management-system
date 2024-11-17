package fs19.java.backend.domain.entity;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {
  private UUID id;
  private String name;
  private String description;
  private ZonedDateTime createdDate;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private UUID createdByUserId;
  private UUID workspaceId;
  private Boolean status;
}
