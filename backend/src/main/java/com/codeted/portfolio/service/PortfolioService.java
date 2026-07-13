package com.codeted.portfolio.service;

import com.codeted.portfolio.dto.CreatePortfolioItemRequest;
import com.codeted.portfolio.dto.PortfolioItemResponse;
import com.codeted.portfolio.dto.UpdatePortfolioItemRequest;

import java.util.List;
import java.util.UUID;

public interface PortfolioService {

    List<PortfolioItemResponse> findPublicItems();

    List<PortfolioItemResponse> findAll();

    PortfolioItemResponse create(CreatePortfolioItemRequest request);

    PortfolioItemResponse update(UUID publicId, UpdatePortfolioItemRequest request);

    void delete(UUID publicId);
}
