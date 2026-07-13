package com.codeted.portal.dto;

import com.codeted.project.dto.ProjectResponse;
import com.codeted.quote.dto.QuoteResponse;

import java.util.List;

public record ClientPortalResponse(
        String customerName,
        String email,
        String company,
        List<ProjectResponse> projects,
        List<QuoteResponse> quotes
) {
}
