package org.bszabat.todomanagement.adapter.inbound.dto;


import jakarta.validation.constraints.NotBlank;

public record NewTaskDto(
        @NotBlank String title,
        @NotBlank String description) {
}
