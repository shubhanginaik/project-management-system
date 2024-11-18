package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {


    Task createTask(TaskRequestDTO taskRequestDTO, User user, User assignedUser);

    Task updateTask(UUID taskId, TaskRequestDTO taskRequestDTO, User assignedUser);

    Task deleteTask(UUID taskId);

    List<Task> getTasks();

    Task getTaskById(UUID taskId);

    List<Task> getTasksByAssignedUserId(UUID userId);

    List<Task> getTasksByCreatedUserId(UUID createdUserId);
}
