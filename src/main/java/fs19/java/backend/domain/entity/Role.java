package fs19.java.backend.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Responsible to work as a base model for a role object
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    private UUID id;
    @NotNull
    private String name;
    private ZonedDateTime createdDate;

}
