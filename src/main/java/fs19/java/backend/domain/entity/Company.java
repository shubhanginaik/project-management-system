package fs19.java.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private UUID id;
    private String name;
    private ZonedDateTime createdDate;
    private UUID createdBy;
}
