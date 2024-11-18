package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.TaskServiceImpl;
import fs19.java.backend.application.dto.task.TaskRequestDTO;
import fs19.java.backend.application.dto.task.TaskResponseDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import fs19.java.backend.presentation.shared.response.ResponseHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("app/v1/tasks")
@OpenAPIDefinition(info = @Info(title = "Task API", version = "v1"))
public class TaskController {


    @Autowired
    private TaskServiceImpl taskService;


    @PostMapping
    public ResponseEntity<GlobalResponse<TaskResponseDTO>> createTask(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO theTaskResponse = taskService.createTask(taskRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theTaskResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theTaskResponse, ResponseHandler.convertResponseStatusToError(theTaskResponse.getStatus())), responseCode);
    }


    /**
     * Update a task by id
     *
     * @param taskId
     * @return
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<GlobalResponse<TaskResponseDTO>> updateTask(@PathVariable UUID taskId, @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO theTaskResponse = taskService.updateTask(taskId, taskRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theTaskResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theTaskResponse, ResponseHandler.convertResponseStatusToError(theTaskResponse.getStatus())), responseCode);
    }

    /**
     * Delete Task By Id
     *
     * @param taskId
     * @return
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<GlobalResponse<TaskResponseDTO>> deleteTaskById(@PathVariable UUID taskId) {
        TaskResponseDTO theTaskResponse = taskService.deleteTask(taskId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theTaskResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theTaskResponse, ResponseHandler.convertResponseStatusToError(theTaskResponse.getStatus())), responseCode);
    }

    /**
     * Return the all tasks
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<GlobalResponse<List<TaskResponseDTO>>> getTasks() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), taskService.getTasks()), HttpStatus.OK);
    }


    /**
     * Return the task according to given Id
     *
     * @return
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<GlobalResponse<TaskResponseDTO>> getTaskById(@PathVariable UUID taskId) {
        TaskResponseDTO theTaskResponse = taskService.getTaskById(taskId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theTaskResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theTaskResponse, ResponseHandler.convertResponseStatusToError(theTaskResponse.getStatus())), responseCode);
    }

    /**
     * return tasks according to assigned userId
     * @param assignedUserId
     * @return
     */
    @GetMapping("findByAssigned/{assignedUserId}")
    public ResponseEntity<GlobalResponse<List<TaskResponseDTO>>> getTasksByAssignedUserId(@PathVariable UUID assignedUserId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), taskService.getTasksByAssignedUserId(assignedUserId)), HttpStatus.OK);
    }

    /**
     * return tasks according to created userId
     * @param createdUserId
     * @return
     */
    @GetMapping("findByCreated/{createdUserId}")
    public ResponseEntity<GlobalResponse<List<TaskResponseDTO>>> getTasksByCreatedUserId(@PathVariable UUID createdUserId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), taskService.getTasksByCreatedUserId(createdUserId)), HttpStatus.OK);
    }


}
