package com.codeted.dashboard.dto;

public record DashboardSummaryResponse(
        long totalUsers,
        long activeSessions,
        long publishedInsights,
        long enabledModules
) {
}
