package org.bszabat.todomanagement.adapter.outbound;

import org.bszabat.todomanagement.domain.TaskStatus;

import java.time.LocalDateTime;

public record TaskDto(
        String id,
        String title,
        String description,
        LocalDateTime creationDate,
        TaskStatus status) {
}
