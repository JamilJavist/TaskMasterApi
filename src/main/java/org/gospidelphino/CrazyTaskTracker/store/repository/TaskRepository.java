package org.gospidelphino.CrazyTaskTracker.store.repository;

import org.gospidelphino.CrazyTaskTracker.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
