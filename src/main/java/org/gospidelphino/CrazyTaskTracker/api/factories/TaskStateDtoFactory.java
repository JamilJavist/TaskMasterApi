package org.gospidelphino.CrazyTaskTracker.api.factories;

import lombok.RequiredArgsConstructor;
import org.gospidelphino.CrazyTaskTracker.api.dto.ProjectDto;
import org.gospidelphino.CrazyTaskTracker.api.dto.TaskDto;
import org.gospidelphino.CrazyTaskTracker.api.dto.TaskStateDto;
import org.gospidelphino.CrazyTaskTracker.store.entities.ProjectEntity;
import org.gospidelphino.CrazyTaskTracker.store.entities.TaskStateEntity;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TaskStateDtoFactory {

    private final TaskDtoFactory taskDtoFactory;

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {
        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .leftTaskStateId(entity.getLeftTaskState().map(TaskStateEntity::getId).orElse(null))
                .rightTaskStateId(entity.getRightTaskState().map(TaskStateEntity::getId).orElse(null))
                .createAt(entity.getCreateAt())
                .tasks(
                        entity
                                .getTasks()
                                .stream()
                                .map(taskDtoFactory::makeTaskDto)
                                .collect(Collectors.toList()))
                .build();
    }
}
