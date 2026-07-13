package com.codeted.portfolio.mapper;

import com.codeted.portfolio.dto.PortfolioItemResponse;
import com.codeted.portfolio.entity.PortfolioItem;
import org.springframework.stereotype.Component;

@Component
public class PortfolioItemMapper {

    public PortfolioItemResponse toResponse(PortfolioItem portfolioItem) {
        return new PortfolioItemResponse(
                portfolioItem.getId(),
                portfolioItem.getPublicId(),
                portfolioItem.getTitle(),
                portfolioItem.getSlug(),
                portfolioItem.getCategory(),
                portfolioItem.getSummary(),
                portfolioItem.getChallenge(),
                portfolioItem.getSolution(),
                portfolioItem.getResult(),
                portfolioItem.isFeatured(),
                portfolioItem.isPublished()
        );
    }
}
