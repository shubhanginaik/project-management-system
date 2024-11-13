package fs19.java.backend.application.dto.role;

import fs19.java.backend.presentation.shared.status.ResponseStatus;
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
public class RoleResponseDTO {

    private UUID id;
    private String name;
    private Date created_date;
    private ResponseStatus status;


}
