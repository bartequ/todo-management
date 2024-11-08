package org.bszabat.todomanagement.adapter.outbound.mongo;

import org.bszabat.todomanagement.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataMongoTaskRepository extends ReactiveMongoRepository<Task, String> {
}
