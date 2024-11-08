package org.bszabat.todomanagement.application;

import org.bszabat.todomanagement.adapter.inbound.dto.NewTaskDto;
import org.bszabat.todomanagement.adapter.outbound.TaskDto;
import org.bszabat.todomanagement.domain.Task;
import org.bszabat.todomanagement.domain.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toTaskDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "status", source = "status")
    Task toTask(NewTaskDto taskDto, LocalDateTime creationDate, TaskStatus status);
}
