package org.gospidelphino.CrazyTaskTracker.api.factories;

import org.gospidelphino.CrazyTaskTracker.api.dto.TaskDto;
import org.gospidelphino.CrazyTaskTracker.api.dto.TaskStateDto;
import org.gospidelphino.CrazyTaskTracker.store.entities.TaskEntity;
import org.gospidelphino.CrazyTaskTracker.store.entities.TaskStateEntity;

public class TaskDtoFactory {
    public TaskDto makeTaskDto(TaskEntity entity) {
        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createAt(entity.getCreateAt())
                .build();
    }
}
