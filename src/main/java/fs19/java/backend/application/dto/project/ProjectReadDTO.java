package fs19.java.backend.application.dto.project;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectReadDTO {
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
