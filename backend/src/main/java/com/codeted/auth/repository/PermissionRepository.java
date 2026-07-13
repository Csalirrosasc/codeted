package com.codeted.auth.repository;

import com.codeted.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findAllByOrderByModuleAscCodeAsc();
}
