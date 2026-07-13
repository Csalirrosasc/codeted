package com.codeted.servicecatalog.repository;

import com.codeted.servicecatalog.entity.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {

    List<ServiceOffering> findByActiveTrueOrderByFeaturedDescTitleAsc();

    Optional<ServiceOffering> findByPublicId(UUID publicId);
}
