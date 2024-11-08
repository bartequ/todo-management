package org.bszabat.todomanagement.application;

import lombok.RequiredArgsConstructor;
import org.bszabat.todomanagement.adapter.inbound.dto.NewTaskDto;
import org.bszabat.todomanagement.adapter.inbound.dto.UpdateTaskDto;
import org.bszabat.todomanagement.adapter.outbound.TaskDto;
import org.bszabat.todomanagement.application.config.TimeSupplier;
import org.bszabat.todomanagement.application.exception.TodoManagementNotFoundException;
import org.bszabat.todomanagement.domain.Task;
import org.bszabat.todomanagement.domain.TaskStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TimeSupplier timeSupplier;

    public Flux<TaskDto> getAllTasks() {
        return taskRepository.findAll().map(taskMapper::toTaskDto);
    }

    public Mono<TaskDto> getTaskById(String id) {
        return taskRepository.findById(id)
                .flatMap(taskOpt -> {
                    checkIfTaskExists(taskOpt, id);
                    return Mono.just(taskOpt.get());
                })
                .map(taskMapper::toTaskDto);
    }

    public Mono<TaskDto> createTask(NewTaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto, timeSupplier.nowAsLocalDateTime(), TaskStatus.OPEN);
        return taskRepository.save(task).map(taskMapper::toTaskDto);
    }

    public Mono<TaskDto> updateTask(String id, UpdateTaskDto taskDto) {
        return taskRepository.findById(id)
                .flatMap(taskOpt -> {
                    checkIfTaskExists(taskOpt, id);
                    final var task = taskOpt.get();
                    task.updateTask(taskDto.title(), taskDto.description(), taskDto.status());
                    return taskRepository.save(task);
                })
                .map(taskMapper::toTaskDto);
    }

    public Mono<Void> deleteTask(String id) {
        return taskRepository.deleteById(id);
    }

    private void checkIfTaskExists(Optional<Task> taskOpt, String id) {
        if (taskOpt.isEmpty()) {
            throw new TodoManagementNotFoundException(String.format("Task with provided id: %s not found", id));
        }
    }
}
