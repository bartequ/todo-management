package org.bszabat.todomanagement.adapter.inbound.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.bszabat.todomanagement.domain.TaskStatus;

public record UpdateTaskDto(
        @NotBlank String title,
        @NotBlank String description,
        @NotEmpty TaskStatus status) {
}
