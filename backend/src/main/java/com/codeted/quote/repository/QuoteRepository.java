package com.codeted.quote.repository;

import com.codeted.quote.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Optional<Quote> findByPublicId(UUID publicId);

    List<Quote> findAllByCustomerId(Long customerId);
}
