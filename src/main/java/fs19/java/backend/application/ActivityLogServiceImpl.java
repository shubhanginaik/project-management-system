package fs19.java.backend.application;

import fs19.java.backend.application.dto.activitylog.ActivityLogDTO;
import fs19.java.backend.application.mapper.ActivityLogMapper;
import fs19.java.backend.application.service.ActivityLogService;
import fs19.java.backend.domain.entity.ActivityLog;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.ActivityLogJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.ActivityLogNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    private static final String ACTIVITY_LOG_NOT_FOUND_MESSAGE = "ActivityLog with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User with ID %s not found";

    private final ActivityLogJpaRepo activityLogRepository;
    private final UserJpaRepo userRepository;

    public ActivityLogServiceImpl(ActivityLogJpaRepo activityLogRepository, UserJpaRepo userRepository) {
        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ActivityLogDTO createActivityLog(ActivityLogDTO activityLogDTO) {
        User user = userRepository.findById(activityLogDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, activityLogDTO.getUserId())));
        ActivityLog activityLog = ActivityLogMapper.toEntity(activityLogDTO, user);
        activityLog.setId(UUID.randomUUID());
        activityLog.setCreatedDate(ZonedDateTime.now());
        activityLogRepository.save(activityLog);
        return ActivityLogMapper.toDTO(activityLog);
    }

    @Override
    public ActivityLogDTO updateActivityLog(UUID id, ActivityLogDTO activityLogDTO) {
        ActivityLog existingActivityLog = activityLogRepository.findById(id)
                .orElseThrow(() -> new ActivityLogNotFoundException(String.format(ACTIVITY_LOG_NOT_FOUND_MESSAGE, id)));

        User user = userRepository.findById(activityLogDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, activityLogDTO.getUserId())));

        existingActivityLog.setEntityType(activityLogDTO.getEntityType());
        existingActivityLog.setEntityId(activityLogDTO.getEntityId());
        existingActivityLog.setAction(activityLogDTO.getAction());
        existingActivityLog.setUserId(user);
        activityLogRepository.save(existingActivityLog);
        return ActivityLogMapper.toDTO(existingActivityLog);
    }

    @Override
    public ActivityLogDTO getActivityLogById(UUID id) {
        ActivityLog activityLog = activityLogRepository.findById(id)
                .orElseThrow(() -> new ActivityLogNotFoundException(String.format(ACTIVITY_LOG_NOT_FOUND_MESSAGE, id)));
        return ActivityLogMapper.toDTO(activityLog);
    }

    @Override
    public List<ActivityLogDTO> getAllActivityLogs() {
        return activityLogRepository.findAll().stream()
                .map(ActivityLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteActivityLog(UUID id) {
        if (!activityLogRepository.existsById(id)) {
            throw new ActivityLogNotFoundException(String.format(ACTIVITY_LOG_NOT_FOUND_MESSAGE, id));
        }
        activityLogRepository.deleteById(id);
    }
}