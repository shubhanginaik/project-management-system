package fs19.java.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import fs19.java.backend.domain.entity.enums.ValidPriority;
import fs19.java.backend.domain.entity.enums.ValidTaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Responsible to work as a base model for a task object
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @NotNull
    @Column(nullable = false)
    private String name;
    private String description;
    @NotNull
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime resolvedDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime dueDate;
    private List<String> attachments;
    @Column(nullable = false)
    @ValidTaskStatus
    private String taskStatus;
    @Column(nullable = false)
    private UUID projectId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "createduser_id", nullable = false)
    private User createdUser;
    @ManyToOne
    @JoinColumn(name = "assigneduser_id", nullable = false)
    private User assignedUser;
    @NotNull
    @Column(nullable = false)
    @ValidPriority
    private String priority;

}
