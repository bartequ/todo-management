package org.bszabat.todomanagement.adapter.inbound;

import org.bszabat.todomanagement.adapter.inbound.dto.NewTaskDto;
import org.bszabat.todomanagement.adapter.inbound.dto.UpdateTaskDto;
import org.bszabat.todomanagement.adapter.outbound.TaskDto;
import org.bszabat.todomanagement.application.TaskService;
import org.bszabat.todomanagement.application.exception.ClientError;
import org.bszabat.todomanagement.application.exception.TodoManagementNotFoundException;
import org.bszabat.todomanagement.domain.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TaskService taskService;

    @Test
    void getAllTasksShouldReturnCorrectly() {
        // given
        TaskDto taskDto = generateTaskDto();
        when(taskService.getAllTasks()).thenReturn(Flux.just(taskDto));

        // when & then
        webTestClient.get()
                .uri("/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskDto.class)
                .hasSize(1)
                .contains(taskDto);
    }

    @Test
    void getTaskByIdShouldReturnCorrectly() {
        // given
        TaskDto taskDto = generateTaskDto();
        when(taskService.getTaskById(any())).thenReturn(Mono.just(taskDto));

        // when & then
        webTestClient.get()
                .uri("/tasks/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class)
                .isEqualTo(taskDto);
    }

    @Test
    void getTaskByIdShouldReturn404WhenTaskNotFound() {
        // given
        when(taskService.getTaskById(any())).thenThrow(new TodoManagementNotFoundException("Task not found"));

        // when & then
        webTestClient.get()
                .uri("/tasks/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ClientError.class);
    }

    @Test
    public void createTaskShouldCreateCorrectly() {
        // given
        TaskDto taskDto = generateTaskDto();
        NewTaskDto newTaskDto = generateNewTaskDto();
        when(taskService.createTask(any())).thenReturn(Mono.just(taskDto));

        // when & then
        webTestClient.post()
                .uri("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newTaskDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TaskDto.class)
                .isEqualTo(taskDto);
    }

    @Test
    public void updateTaskShouldCreateCorrectly() {
        // given
        TaskDto taskDto = generateTaskDto();
        UpdateTaskDto updateTaskDto = generateUpdateTaskDto();
        when(taskService.updateTask(any(), any())).thenReturn(Mono.just(taskDto));

        // when & then
        webTestClient.put()
                .uri("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateTaskDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class)
                .isEqualTo(taskDto);
    }

    @Test
    void updateTaskShouldReturn404WhenTaskNotFound() {
        // given
        UpdateTaskDto updateTaskDto = generateUpdateTaskDto();
        when(taskService.updateTask(any(), any())).thenThrow(new TodoManagementNotFoundException("Task not found"));

        // when & then
        webTestClient.put()
                .uri("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateTaskDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ClientError.class);
    }

    @Test
    public void deleteTaskShouldCreateCorrectly() {
        // given
        TaskDto taskDto = generateTaskDto();
        when(taskService.deleteTask(any())).thenReturn(Mono.empty());

        // when & then
        webTestClient.delete()
                .uri("/tasks/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteTaskShouldReturn404WhenTaskNotFound() {
        // given
        when(taskService.deleteTask(any())).thenThrow(new TodoManagementNotFoundException("Task not found"));

        // when & then
        webTestClient.delete()
                .uri("/tasks/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ClientError.class);
    }


    private static TaskDto generateTaskDto() {
        return new TaskDto(
                "id",
                "Sample Task",
                "This is a sample task",
                LocalDateTime.of(2024,11,8,12,10),
                TaskStatus.OPEN);
    }

    private static NewTaskDto generateNewTaskDto() {
        return new NewTaskDto("New Task", "This is a new task");
    }

    private static UpdateTaskDto generateUpdateTaskDto() {
        return new UpdateTaskDto("New Task", "This is a new task", TaskStatus.IN_PROGRESS);
    }
}
