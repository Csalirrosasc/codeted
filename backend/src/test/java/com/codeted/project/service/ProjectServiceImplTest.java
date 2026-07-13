package com.codeted.project.service;

import com.codeted.crm.entity.Customer;
import com.codeted.crm.repository.CustomerRepository;
import com.codeted.project.dto.CreateProjectRequest;
import com.codeted.project.dto.ProjectResponse;
import com.codeted.project.entity.Project;
import com.codeted.project.mapper.ProjectMapper;
import com.codeted.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void createShouldBindCustomerResolvedByPublicId() {
        UUID customerPublicId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(5L);
        customer.setPublicId(customerPublicId);
        customer.setFullName("Cliente Uno");

        when(customerRepository.findByPublicId(customerPublicId)).thenReturn(Optional.of(customer));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(projectMapper.toResponse(any(Project.class))).thenReturn(
                new ProjectResponse(1L, UUID.randomUUID(), customerPublicId, "Cliente Uno", "Proyecto CRM", "Descripcion", "planning", LocalDate.now(), null)
        );

        ProjectResponse response = projectService.create(
                new CreateProjectRequest(customerPublicId, "Proyecto CRM", "Descripcion", "planning", LocalDate.now(), null)
        );

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertThat(captor.getValue().getCustomer().getPublicId()).isEqualTo(customerPublicId);
        assertThat(response.customerPublicId()).isEqualTo(customerPublicId);
    }
}
