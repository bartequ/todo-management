package org.bszabat.todomanagement.adapter.outbound.mongo;

import lombok.RequiredArgsConstructor;
import org.bszabat.todomanagement.application.TaskRepository;
import org.bszabat.todomanagement.domain.Task;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoTaskRepository implements TaskRepository {
    private final SpringDataMongoTaskRepository repository;

    @Override
    public Mono<Optional<Task>> findById(String id) {
        return repository.findById(id)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty());
    }

    @Override
    public Flux<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Task> save(Task task) {
        return repository.save(task);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
