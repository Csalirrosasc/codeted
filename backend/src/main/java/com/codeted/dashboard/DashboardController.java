package com.codeted.dashboard;

import com.codeted.common.ApiResponse;
import com.codeted.dashboard.dto.DashboardSummaryResponse;
import com.codeted.dashboard.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Get dashboard summary", description = "Returns the main summary cards for the administrative dashboard.")
    @GetMapping("/summary")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EDITOR')")
    public ApiResponse<DashboardSummaryResponse> getSummary() {
        return ApiResponse.success("Resumen del dashboard obtenido correctamente", dashboardService.getSummary());
    }
}
