package com.codeted.auth.repository;

import com.codeted.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    Optional<Role> findByPublicId(UUID publicId);

    List<Role> findAllByOrderByNameAsc();
}
