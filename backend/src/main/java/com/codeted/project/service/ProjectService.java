package com.codeted.project.service;

import com.codeted.project.dto.CreateProjectRequest;
import com.codeted.project.dto.ProjectResponse;
import com.codeted.project.dto.UpdateProjectRequest;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    List<ProjectResponse> findAll();

    ProjectResponse create(CreateProjectRequest request);

    ProjectResponse update(UUID publicId, UpdateProjectRequest request);

    void delete(UUID publicId);
}
