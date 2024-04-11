package org.gospidelphino.CrazyTaskTracker.api.factories;

import org.gospidelphino.CrazyTaskTracker.api.dto.ProjectDto;
import org.gospidelphino.CrazyTaskTracker.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity entity) {

        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createAt(entity.getCreateAt())
                .build();
    }
}
