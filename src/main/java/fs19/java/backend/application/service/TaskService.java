package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskResponseDTO createTask(@Valid TaskRequestDTO taskRequestDTO);

    TaskResponseDTO updateTask(UUID taskId, @Valid TaskRequestDTO taskRequestDTO);

    TaskResponseDTO deleteTask(UUID taskId);

    List<TaskResponseDTO> getTasks();

    TaskResponseDTO getTaskById(UUID taskId);

    List<TaskResponseDTO> getTasksByAssignedUserId(UUID userId);

    List<TaskResponseDTO> getTasksByCreatedUserId(UUID createdUserId);
}


