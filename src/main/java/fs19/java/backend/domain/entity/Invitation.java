package fs19.java.backend.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invitation {
    private UUID id;
    private boolean isAccepted;
    @NotNull
    private ZonedDateTime expiredAt;
    @NotNull
    private String email;
    @NotNull
    private Role role;
    @NotNull
    private Company company;
}
