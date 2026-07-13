package com.codeted.project.repository;

import com.codeted.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByPublicId(UUID publicId);

    List<Project> findAllByCustomerId(Long customerId);
}
