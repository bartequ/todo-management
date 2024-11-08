package org.bszabat.todomanagement.application;

import org.bszabat.todomanagement.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface TaskRepository {
    Mono<Optional<Task>> findById(String id);
    Flux<Task> findAll();
    Mono<Task> save(Task task);
    Mono<Void> deleteById(String id);
}
