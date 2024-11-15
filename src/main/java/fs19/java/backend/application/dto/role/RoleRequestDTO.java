package fs19.java.backend.application.dto.role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleRequestDTO {

    private UUID id;
    @NotNull
    private String name;
    private Date created_date;

    public RoleRequestDTO(String name, Date created_date) {
        this.created_date = created_date;
        this.name = name;
    }
}
