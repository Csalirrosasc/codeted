package com.codeted.contact.repository;

import com.codeted.contact.entity.ContactLead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContactLeadRepository extends JpaRepository<ContactLead, Long> {

    Optional<ContactLead> findByPublicId(UUID publicId);

    long countByStatusIgnoreCase(String status);
}
