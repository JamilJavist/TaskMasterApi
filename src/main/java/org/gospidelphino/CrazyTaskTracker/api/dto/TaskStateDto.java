package org.gospidelphino.CrazyTaskTracker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStateDto {
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Long ordinal;

    @JsonProperty("left_task_state_id")
    private Long leftTaskStateId;

    @JsonProperty("right_task_state_id")
    private Long rightTaskStateId;

    @NonNull
    @JsonProperty("created_at")
    private Instant createAt;

    @NonNull
    List<TaskDto> tasks;
}
