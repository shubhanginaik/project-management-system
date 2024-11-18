package fs19.java.backend.domain.entity;

import fs19.java.backend.domain.entity.enums.Priority;
import fs19.java.backend.domain.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {

    private UUID id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private ZonedDateTime createdDate;
    private ZonedDateTime resolvedDate;
    private ZonedDateTime dueDate;
    private List<String> attachments;
    private TaskStatus taskStatus;
    private UUID projectId;
    @NotNull
    private User createdUser;
    private User assignedUser;
    private Priority priority;

}
