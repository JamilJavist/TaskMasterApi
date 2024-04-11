package org.gospidelphino.CrazyTaskTracker.api.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gospidelphino.CrazyTaskTracker.api.controllers.helpers.ControllerHelper;
import org.gospidelphino.CrazyTaskTracker.api.dto.AckDto;
import org.gospidelphino.CrazyTaskTracker.api.dto.ProjectDto;
import org.gospidelphino.CrazyTaskTracker.api.exceptions.BadRequestException;
import org.gospidelphino.CrazyTaskTracker.api.exceptions.NotFoundException;
import org.gospidelphino.CrazyTaskTracker.api.factories.ProjectDtoFactory;
import org.gospidelphino.CrazyTaskTracker.store.entities.ProjectEntity;
import org.gospidelphino.CrazyTaskTracker.store.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional
@RestController
public class ProjectController {

    private final ProjectDtoFactory projectDtoFactory;
    private final ProjectRepository projectRepository;
    private final ControllerHelper controllerHelper;

    public static final String FETCH_PROJECT = "/api/projects";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";
    public static final String CREATE_OR_UPDATE = "/api/projects";

    @GetMapping(FETCH_PROJECT)
    public List<ProjectDto> fetchProject(
            @RequestParam(value = "prefix_name",required = false) Optional<String> optionalPrefixName
    ) {
        optionalPrefixName = optionalPrefixName.filter(prefixname -> !prefixname.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    @PutMapping(CREATE_OR_UPDATE)
    public ProjectDto createOrUpdateProject(
            @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
            @RequestParam(value = "project_name", required = false) Optional<String> optionalProjectName
    ) {

        optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());

        boolean isCreate = !optionalProjectId.isPresent();

        if (isCreate && !optionalProjectName.isPresent()) {
            throw new BadRequestException("Project name can't be empty");
        }

        ProjectEntity project = optionalProjectId
                .map(controllerHelper::getProjectOrThrowException)
                .orElseGet(() -> ProjectEntity.builder().build());

        optionalProjectName
                .ifPresent(
                        projectName -> {
                            projectRepository
                                    .findByName(projectName)
                                    .filter(anotherProject -> Objects.equals(anotherProject.getId(), project.getId()))
                                    .ifPresent(anotherProject -> {
                                        throw new BadRequestException(String.format("Project \"%s\" already exists.", projectName));
                                    });
                            project.setName(projectName);
                        });

        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeProjectDto(savedProject);
    }

    @DeleteMapping(DELETE_PROJECT)
    public AckDto deleteProject(@PathVariable("project_id") Long projectId) {
        controllerHelper.getProjectOrThrowException(projectId);

        projectRepository.deleteById(projectId);

        return AckDto.makeDefault(true);
    }
}
