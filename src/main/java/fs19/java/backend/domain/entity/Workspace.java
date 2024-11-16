package fs19.java.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Workspace {
    private UUID id;
    private String name;
    private String description;
    private WorkspaceType type;
    private ZonedDateTime createdDate;
    private UUID createdBy;
    private UUID companyId;
}