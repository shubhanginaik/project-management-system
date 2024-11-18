package fs19.java.backend.application;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
import fs19.java.backend.application.mapper.TaskMapper;
import fs19.java.backend.application.service.TaskService;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.TaskRepoImpl;
import fs19.java.backend.infrastructure.UserRepositoryImpl;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {


    private final UserRepositoryImpl userRepository;
    private final TaskRepoImpl taskRepo;

    public TaskServiceImpl(UserRepositoryImpl userRepository, TaskRepoImpl taskRepo) {
        this.userRepository = userRepository;
        this.taskRepo = taskRepo;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO.getName().isEmpty()) { // expected valid name only and that validation is enough
            System.out.println("Task Name from DTO is null, cannot proceed with Task creation.");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_NAME_NOT_FOUND);
        }
        Optional<User> createdUserById = userRepository.findById(taskRequestDTO.getCreatedUserId());
        if (createdUserById.isPresent()) {
            User assignedUser = null;
            if (taskRequestDTO.getAssignedUserId() != null) {
                Optional<User> assignedUserById = userRepository.findById(taskRequestDTO.getCreatedUserId());
                if (assignedUserById.isEmpty()) {
                    System.out.println("Assigned User-Not Found");
                    return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_ASSIGNED_USER_NOT_FOUND);
                }
                assignedUser = assignedUserById.get();
            }

            Task task = taskRepo.createTask(taskRequestDTO, createdUserById.get(), assignedUser);
            return TaskMapper.toTaskResponseDTO(task, ResponseStatus.SUCCESSFULLY_CREATED);

        } else {
            System.out.println("Created User-Not Found");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_CREATED_USER_NOT_FOUND);
        }
    }

    @Override
    public TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO taskRequestDTO) {
        if (taskId == null) {
            System.out.println("Task Id is null, cannot proceed with Task update.");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_ID_NOT_FOUND);
        }
        if (taskRequestDTO.getName().isEmpty()) { // expected valid name only and that validation is enough
            System.out.println("Task Name from DTO is null, cannot proceed with Task creation.");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_NAME_NOT_FOUND);
        }
        Optional<User> createdUserById = userRepository.findById(taskRequestDTO.getCreatedUserId());
        if (createdUserById.isPresent()) {
            User assignedUser = null;
            if (taskRequestDTO.getAssignedUserId() != null) {
                Optional<User> assignedUserById = userRepository.findById(taskRequestDTO.getCreatedUserId());
                if (assignedUserById.isEmpty()) {
                    System.out.println("Assigned User-Not Found");
                    return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_ASSIGNED_USER_NOT_FOUND);
                }
                assignedUser = assignedUserById.get();
            }
            Task task = taskRepo.updateTask(taskId, taskRequestDTO, assignedUser);
            return TaskMapper.toTaskResponseDTO(task, ResponseStatus.SUCCESSFULLY_UPDATED);

        } else {
            System.out.println("Created User-Not Found");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_LEVEL_CREATED_USER_NOT_FOUND);
        }
    }

    @Override
    public TaskResponseDTO deleteTask(UUID taskId) {
        if (taskId.toString() == null) {
            System.out.println("Task ID is null, cannot proceed with delete.");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_ID_NOT_FOUND);
        }
        Task myTask = this.taskRepo.deleteTask(taskId);
        if (myTask == null) {
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.INVALID_INFORMATION_TASK_DETAILS_NOT_FOUND);
        }
        System.out.println("Task-Deleted successfully");
        return TaskMapper.toTaskResponseDTO(myTask, ResponseStatus.SUCCESSFULLY_DELETED);
    }

    @Override
    public List<TaskResponseDTO> getTasks() {
        return TaskMapper.toTaskResponseDTOs(taskRepo.getTasks(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public TaskResponseDTO getTaskById(UUID taskId) {
        if (taskId.toString() == null) {
            System.out.println("Task ID is null, cannot proceed with search.");
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.TASK_ID_NOT_FOUND);
        }
        Task myTask = taskRepo.getTaskById(taskId);
        if (myTask == null) {
            return TaskMapper.toTaskResponseDTO(new Task(), ResponseStatus.INVALID_INFORMATION_TASK_DETAILS_NOT_FOUND);
        }
        return TaskMapper.toTaskResponseDTO(myTask, ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public List<TaskResponseDTO> getTasksByAssignedUserId(UUID userId) {
        return TaskMapper.toTaskResponseDTOs(taskRepo.getTasksByAssignedUserId(userId), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public List<TaskResponseDTO> getTasksByCreatedUserId(UUID createdUserId) {
        return TaskMapper.toTaskResponseDTOs(taskRepo.getTasksByCreatedUserId(createdUserId), ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
