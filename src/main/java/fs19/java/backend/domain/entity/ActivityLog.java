package fs19.java.backend.domain.entity;

import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity_log")
public class ActivityLog {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column
    private EntityType entityType;

    @GeneratedValue(generator = "UUID")
    private UUID entityId;

    @Column
    private ActionType action;
    @Column
    private ZonedDateTime createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
}