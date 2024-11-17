package fs19.java.backend.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private UUID id;
    private UUID taskId;
    private String content;
    private ZonedDateTime createdDate;
    private UUID createdBy;
}
