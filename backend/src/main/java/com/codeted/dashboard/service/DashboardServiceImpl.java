package com.codeted.dashboard.service;

import com.codeted.blog.repository.BlogPostRepository;
import com.codeted.auth.repository.RefreshTokenRepository;
import com.codeted.auth.repository.UserRepository;
import com.codeted.dashboard.dto.DashboardSummaryResponse;
import com.codeted.portfolio.repository.PortfolioItemRepository;
import com.codeted.servicecatalog.repository.ServiceOfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlogPostRepository blogPostRepository;
    private final PortfolioItemRepository portfolioItemRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public DashboardSummaryResponse getSummary() {
        return new DashboardSummaryResponse(
                userRepository.count(),
                refreshTokenRepository.countByRevokedFalse(),
                blogPostRepository.countByPublishedTrue(),
                countEnabledModules()
        );
    }

    private long countEnabledModules() {
        long enabledModules = 5L;

        if (serviceOfferingRepository.count() > 0) {
            enabledModules++;
        }

        if (portfolioItemRepository.count() > 0) {
            enabledModules++;
        }

        if (blogPostRepository.count() > 0) {
            enabledModules++;
        }

        return enabledModules;
    }
}
