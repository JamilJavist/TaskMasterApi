package org.gospidelphino.CrazyTaskTracker.api.controllers.helpers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gospidelphino.CrazyTaskTracker.api.exceptions.NotFoundException;
import org.gospidelphino.CrazyTaskTracker.store.entities.ProjectEntity;
import org.gospidelphino.CrazyTaskTracker.store.repository.ProjectRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Transactional
public class ControllerHelper {

    private final ProjectRepository projectRepository;

    public ProjectEntity getProjectOrThrowException(Long projectId) {

        return projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Project with \"%s\" doesn't exist.",
                                        projectId
                                )
                        )
                );
    }
}
