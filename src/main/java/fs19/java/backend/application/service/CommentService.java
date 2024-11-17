package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.application.dto.comment.CommentUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO);
    CommentResponseDTO updateComment(UUID id, CommentUpdateDTO commentUpdateDTO);
    CommentResponseDTO getCommentById(UUID id);
    List<CommentResponseDTO> getAllComments();
    void deleteComment(UUID id);
}