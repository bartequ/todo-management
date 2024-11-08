package org.bszabat.todomanagement.adapter.inbound;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.bszabat.todomanagement.adapter.inbound.dto.NewTaskDto;
import org.bszabat.todomanagement.adapter.inbound.dto.UpdateTaskDto;
import org.bszabat.todomanagement.adapter.outbound.TaskDto;
import org.bszabat.todomanagement.application.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Get task by ID", description = "Returns a single task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TaskDto> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Create a new task", description = "Creates a new task with the given details")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TaskDto> createTask(@RequestBody NewTaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @Operation(summary = "Update task", description = "Updates an existing task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TaskDto> updateTask(@PathVariable String id, @RequestBody UpdateTaskDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @Operation(summary = "Delete task", description = "Deletes a task by ID")
    @ApiResponse(responseCode = "204", description = "Task deleted successfully")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id);
    }
}
