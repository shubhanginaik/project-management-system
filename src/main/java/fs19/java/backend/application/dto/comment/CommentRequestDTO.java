package fs19.java.backend.application.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {

    @Schema(type = "uuid", format = "uuid", description = "The task id", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull(message = "Task id cannot be null")
    private UUID taskId;

    @Schema(type = "string", description = "The content of the comment", example = "This is a comment")
    @NotBlank(message = "Content cannot be null")
    private String content;

    @Schema(type = "uuid", format = "uuid", description = "Unique identifier of the user who created the comment")
    @NotNull(message = "Created by user ID is required")
    private UUID createdBy;
}