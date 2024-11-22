package fs19.java.backend.application;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentResponseDTO;
import fs19.java.backend.application.dto.comment.CommentUpdateDTO;
import fs19.java.backend.application.mapper.CommentMapper;
import fs19.java.backend.application.service.CommentService;
import fs19.java.backend.domain.entity.Comment;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.CommentJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.TaskJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.CommentNotFoundException;
import fs19.java.backend.presentation.shared.exception.TaskLevelException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private static final String COMMENT_NOT_FOUND_MESSAGE = "Comment with ID %s not found";
    private static final String TASK_NOT_FOUND_MESSAGE = "Task with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User with ID %s not found";

    private final CommentJpaRepo commentRepository;
    private final TaskJpaRepo taskRepository;
    private final UserJpaRepo userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentJpaRepo commentRepository, TaskJpaRepo taskRepository, UserJpaRepo userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
        if (commentRequestDTO == null) {
            throw new IllegalArgumentException("CommentRequestDTO must not be null");
        }

        User createdBy = userRepository.findById(commentRequestDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, commentRequestDTO.getCreatedBy())));
        Task task = taskRepository.findById(commentRequestDTO.getTaskId())
                .orElseThrow(() -> new TaskLevelException(String.format(TASK_NOT_FOUND_MESSAGE, commentRequestDTO.getTaskId())));

        Comment comment = commentMapper.toEntity(commentRequestDTO, task, createdBy);
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