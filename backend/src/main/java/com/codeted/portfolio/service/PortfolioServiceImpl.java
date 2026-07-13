package com.codeted.portfolio.service;

import com.codeted.portfolio.dto.CreatePortfolioItemRequest;
import com.codeted.portfolio.dto.PortfolioItemResponse;
import com.codeted.portfolio.dto.UpdatePortfolioItemRequest;
import com.codeted.portfolio.entity.PortfolioItem;
import com.codeted.portfolio.mapper.PortfolioItemMapper;
import com.codeted.portfolio.repository.PortfolioItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioItemRepository portfolioItemRepository;
    private final PortfolioItemMapper portfolioItemMapper;

    @Override
    public List<PortfolioItemResponse> findPublicItems() {
        return portfolioItemRepository.findByPublishedTrueOrderByFeaturedDescTitleAsc()
                .stream()
                .map(portfolioItemMapper::toResponse)
                .toList();
    }

    @Override
    public List<PortfolioItemResponse> findAll() {
        return portfolioItemRepository.findAll().stream()
                .sorted(Comparator.comparing(PortfolioItem::isFeatured).reversed()
                        .thenComparing(PortfolioItem::getTitle))
                .map(portfolioItemMapper::toResponse)
                .toList();
    }

    @Override
    public PortfolioItemResponse create(CreatePortfolioItemRequest request) {
        PortfolioItem portfolioItem = new PortfolioItem();
        applyChanges(portfolioItem, request.title(), request.slug(), request.category(), request.summary(), request.challenge(), request.solution(), request.result(), request.featured(), request.published());
        return portfolioItemMapper.toResponse(portfolioItemRepository.save(portfolioItem));
    }

    @Override
    public PortfolioItemResponse update(UUID publicId, UpdatePortfolioItemRequest request) {
        PortfolioItem portfolioItem = portfolioItemRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Portafolio no encontrado"));
        applyChanges(portfolioItem, request.title(), request.slug(), request.category(), request.summary(), request.challenge(), request.solution(), request.result(), request.featured(), request.published());
        return portfolioItemMapper.toResponse(portfolioItemRepository.save(portfolioItem));
    }

    @Override
    public void delete(UUID publicId) {
        PortfolioItem portfolioItem = portfolioItemRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Portafolio no encontrado"));
        portfolioItemRepository.delete(portfolioItem);
    }

    private void applyChanges(PortfolioItem portfolioItem, String title, String slug, String category, String summary, String challenge, String solution, String result, boolean featured, boolean published) {
        portfolioItem.setTitle(title);
        portfolioItem.setSlug(slug);
        portfolioItem.setCategory(category);
        portfolioItem.setSummary(summary);
        portfolioItem.setChallenge(challenge);
        portfolioItem.setSolution(solution);
        portfolioItem.setResult(result);
        portfolioItem.setFeatured(featured);
        portfolioItem.setPublished(published);
    }
}
