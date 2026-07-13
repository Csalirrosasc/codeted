package com.codeted.portfolio.repository;

import com.codeted.portfolio.entity.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {

    List<PortfolioItem> findByPublishedTrueOrderByFeaturedDescTitleAsc();

    Optional<PortfolioItem> findByPublicId(UUID publicId);
}
