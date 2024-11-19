package fs19.java.backend.domain.entity;

import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Responsible to work as a base model for a role object
 */
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private UUID id;
    @NotNull
    private String name;

}
