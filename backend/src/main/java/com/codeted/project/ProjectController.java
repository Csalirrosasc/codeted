package com.codeted.project;

import com.codeted.common.ApiResponse;
import com.codeted.project.dto.CreateProjectRequest;
import com.codeted.project.dto.ProjectResponse;
import com.codeted.project.dto.UpdateProjectRequest;
import com.codeted.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @PreAuthorize("hasAuthority('projects.read')")
    @Operation(summary = "List projects", description = "Returns the current internal projects.")
    public ApiResponse<List<ProjectResponse>> findAll() {
        return ApiResponse.success("Proyectos obtenidos correctamente", projectService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('projects.write')")
    @Operation(summary = "Create project", description = "Creates a new project linked to a customer.")
    public ApiResponse<ProjectResponse> create(@Valid @RequestBody CreateProjectRequest request) {
        return ApiResponse.success("Proyecto creado correctamente", projectService.create(request));
    }

    @PatchMapping("/{publicId}")
    @PreAuthorize("hasAuthority('projects.write')")
    public ApiResponse<ProjectResponse> update(@PathVariable UUID publicId, @Valid @RequestBody UpdateProjectRequest request) {
        return ApiResponse.success("Proyecto actualizado correctamente", projectService.update(publicId, request));
    }

    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('projects.write')")
    public ApiResponse<Void> delete(@PathVariable UUID publicId) {
        projectService.delete(publicId);
        return ApiResponse.success("Proyecto eliminado correctamente");
    }
}
