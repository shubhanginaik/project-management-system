package fs19.java.backend.application.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentResponseDTO {

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the comment")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Schema(type = "uuid", format = "uuid", description = "The task id")
    private UUID taskId;

    @Schema(type = "string", format = "string", description = "The content of the comment")
    private String content;

    @Schema(type = "string", format = "string", description = "The created date of the comment")
    private ZonedDateTime createdDate;

    @Schema(type = "UUID", format = "uuid", description = "Unique identifier of the user who created the comment")
    private UUID createdBy;
}