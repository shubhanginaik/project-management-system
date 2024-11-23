package fs19.java.backend.application;

import fs19.java.backend.application.dto.notification.NotificationDTO;
import fs19.java.backend.application.mapper.NotificationMapper;
import fs19.java.backend.application.service.NotificationService;
import fs19.java.backend.domain.entity.Notification;
import fs19.java.backend.domain.entity.Project;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.NotificationJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.ProjectJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.NotificationNotFoundException;
import fs19.java.backend.presentation.shared.exception.ProjectNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final String NOTIFICATION_NOT_FOUND_MESSAGE = "Notification with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User with ID %s not found";
    private static final String PROJECT_NOT_FOUND_MESSAGE = "Project with ID %s not found";

    private final NotificationJpaRepo notificationRepository;
    private final UserJpaRepo userRepository;
    private final ProjectJpaRepo projectRepository;

    public NotificationServiceImpl(NotificationJpaRepo notificationRepository, UserJpaRepo userRepository, ProjectJpaRepo projectRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        if (notificationDTO == null) {
            throw new IllegalArgumentException("NotificationDTO cannot be null.");
        }

        User mentionedBy = userRepository.findById(notificationDTO.getMentionedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, notificationDTO.getMentionedBy())));
        User mentionedTo = userRepository.findById(notificationDTO.getMentionedTo())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, notificationDTO.getMentionedTo())));
        Project project = projectRepository.findById(notificationDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(String.format(PROJECT_NOT_FOUND_MESSAGE, notificationDTO.getProjectId())));

        Notification notification = NotificationMapper.toEntity(notificationDTO, project, mentionedBy, mentionedTo);
        notification.setCreatedDate(ZonedDateTime.now());

        Notification savedNotification = notificationRepository.save(notification);
        return NotificationMapper.toDTO(savedNotification);
    }

    @Override
    public NotificationDTO updateNotification(UUID id, NotificationDTO notificationDTO) {
        if (notificationDTO == null) {
            throw new IllegalArgumentException("NotificationDTO cannot be null.");
        }

        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(String.format(NOTIFICATION_NOT_FOUND_MESSAGE, id)));

        boolean isUpdateProvided = notificationDTO.getContent() != null ||
                notificationDTO.getNotifyType() != null ||
                notificationDTO.getProjectId() != null ||
                notificationDTO.getMentionedBy() != null ||
                notificationDTO.getMentionedTo() != null ||
                notificationDTO.isRead();

        if (!isUpdateProvided) {
            throw new IllegalArgumentException("At least one field must be provided to update the notification.");
        }

        if (notificationDTO.getContent() != null && !notificationDTO.getContent().trim().isEmpty()) {
            existingNotification.setContent(notificationDTO.getContent());
        } else if (notificationDTO.getContent() != null && notificationDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Notification content cannot be blank.");
        }

        if (notificationDTO.getNotifyType() != null) {
            existingNotification.setNotifyType(notificationDTO.getNotifyType());
        }

        if (notificationDTO.getProjectId() != null) {
            Project project = projectRepository.findById(notificationDTO.getProjectId())
                    .orElseThrow(() -> new ProjectNotFoundException(String.format(PROJECT_NOT_FOUND_MESSAGE, notificationDTO.getProjectId())));
            existingNotification.setProjectId(project);
        }

        if (notificationDTO.getMentionedBy() != null) {
            User mentionedBy = userRepository.findById(notificationDTO.getMentionedBy())
                    .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, notificationDTO.getMentionedBy())));
            existingNotification.setMentionedBy(mentionedBy);
        }

        if (notificationDTO.getMentionedTo() != null) {
            User mentionedTo = userRepository.findById(notificationDTO.getMentionedTo())
                    .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, notificationDTO.getMentionedTo())));
            existingNotification.setMentionedTo(mentionedTo);
        }

        existingNotification.setRead(notificationDTO.isRead());

        Notification savedNotification = notificationRepository.save(existingNotification);
        return NotificationMapper.toDTO(savedNotification);
    }

    @Override
    public NotificationDTO getNotificationById(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(String.format(NOTIFICATION_NOT_FOUND_MESSAGE, id)));
        return NotificationMapper.toDTO(notification);
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(NotificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotification(UUID id) {
        if (!notificationRepository.existsById(id)) {
            throw new NotificationNotFoundException(String.format(NOTIFICATION_NOT_FOUND_MESSAGE, id));
        }
        notificationRepository.deleteById(id);
    }
}