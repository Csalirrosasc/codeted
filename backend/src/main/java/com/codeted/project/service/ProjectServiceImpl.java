package com.codeted.project.service;

import com.codeted.crm.entity.Customer;
import com.codeted.crm.repository.CustomerRepository;
import com.codeted.project.dto.CreateProjectRequest;
import com.codeted.project.dto.ProjectResponse;
import com.codeted.project.dto.UpdateProjectRequest;
import com.codeted.project.entity.Project;
import com.codeted.project.mapper.ProjectMapper;
import com.codeted.project.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream().map(projectMapper::toResponse).toList();
    }

    @Override
    public ProjectResponse create(CreateProjectRequest request) {
        Customer customer = customerRepository.findByPublicId(request.customerPublicId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        Project project = new Project();
        applyChanges(project, customer, request.name(), request.description(), request.status(), request.startDate(), request.endDate());

        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse update(UUID publicId, UpdateProjectRequest request) {
        Project project = projectRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));
        Customer customer = customerRepository.findByPublicId(request.customerPublicId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        applyChanges(project, customer, request.name(), request.description(), request.status(), request.startDate(), request.endDate());
        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public void delete(UUID publicId) {
        Project project = projectRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));
        projectRepository.delete(project);
    }

    private void applyChanges(Project project, Customer customer, String name, String description, String status, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        project.setCustomer(customer);
        project.setName(name);
        project.setDescription(description);
        project.setStatus(status);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }
}
