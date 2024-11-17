package fs19.java.backend.application;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.application.dto.comment.CommentUpdateDTO;
import fs19.java.backend.application.mapper.CommentMapper;
import fs19.java.backend.application.service.CommentService;
import fs19.java.backend.domain.abstraction.CommentRepository;
import fs19.java.backend.domain.entity.Comment;
import fs19.java.backend.presentation.shared.exception.CommentNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private static final String COMMENT_NOT_FOUND_MESSAGE   = "Comment with ID %s not found";

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
        if (commentRequestDTO == null) {
            throw new IllegalArgumentException("CommentRequestDTO must not be null");
        }

        Comment comment = commentMapper.toEntity(commentRequestDTO);
        comment.setId(UUID.randomUUID());
        comment.setCreatedDate(ZonedDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    public CommentResponseDTO updateComment(UUID id, CommentUpdateDTO commentUpdateDTO) {
       Comment existingComment = commentRepository.findById(id)
               .orElseThrow(() -> new CommentNotFoundException(String.format(COMMENT_NOT_FOUND_MESSAGE, id)));
         existingComment.setContent(commentUpdateDTO.getContent());
            commentRepository.save(existingComment);
            return commentMapper.toDTO(existingComment);
    }

    @Override
    public CommentResponseDTO getCommentById(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(String.format(COMMENT_NOT_FOUND_MESSAGE, id)));
        return commentMapper.toDTO(comment);
    }

    @Override
    public List<CommentResponseDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(UUID id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException(String.format(COMMENT_NOT_FOUND_MESSAGE, id));
        }
        commentRepository.deleteById(id);
    }
}
