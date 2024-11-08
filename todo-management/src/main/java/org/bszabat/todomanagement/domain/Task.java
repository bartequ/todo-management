package org.bszabat.todomanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private TaskStatus status;

    public void updateTask(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
