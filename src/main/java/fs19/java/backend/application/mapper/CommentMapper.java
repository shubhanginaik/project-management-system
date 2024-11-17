package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.domain.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentRequestDTO commentRequestDTO) {
        return new Comment(
                null,
                commentRequestDTO.getTaskId(),
                commentRequestDTO.getContent(),
                null,
                commentRequestDTO.getCreatedBy()
                );
    }

    public CommentResponseDTO toDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getTaskId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getCreatedBy()
        );
    }
}
