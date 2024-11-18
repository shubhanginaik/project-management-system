package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.domain.abstraction.TaskRepository;
import fs19.java.backend.domain.entity.Task;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.tempMemory.RoleInMemoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TaskRepoImpl implements TaskRepository {

    @Autowired
    private RoleInMemoryDB tempRoleDB;

    @Override
    public Task createTask(TaskRequestDTO taskRequestDTO, User createUser, User assignedUser) {
        return tempRoleDB.createTask(taskRequestDTO, createUser, assignedUser);
    }

    @Override
    public Task updateTask(UUID taskId, TaskRequestDTO taskRequestDTO, User assignedUser) {
        return tempRoleDB.updateTask(taskId, taskRequestDTO, assignedUser);
    }

    @Override
    public Task deleteTask(UUID taskId) {
        return tempRoleDB.deleteTask(taskId);
    }

    @Override
    public List<Task> getTasks() {
        return tempRoleDB.findAllTasks();
    }

    @Override
    public Task getTaskById(UUID taskId) {
        return tempRoleDB.findTaskById(taskId);
    }

    @Override
    public List<Task> getTasksByAssignedUserId(UUID userId) {
       return tempRoleDB.getTasksByAssignedUserId(userId);
    }

    @Override
    public List<Task> getTasksByCreatedUserId(UUID createdUserId) {
        return tempRoleDB.getTasksByCreatedUserId(createdUserId);
    }
}
