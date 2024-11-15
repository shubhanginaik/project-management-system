package fs19.java.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
    private String name;
    private Date created_date;

}
