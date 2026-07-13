package com.codeted.project.mapper;

import com.codeted.project.dto.ProjectResponse;
import com.codeted.project.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getPublicId(),
                project.getCustomer().getPublicId(),
                project.getCustomer().getFullName(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                project.getStartDate(),
                project.getEndDate()
        );
    }
}
