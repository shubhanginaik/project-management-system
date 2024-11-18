package fs19.java.backend.application.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDTO {

    @Schema(type = "string", description = "The content of the comment", example = "This is a comment")
    @NotBlank(message = "Content cannot be null")
    private String content;
}

