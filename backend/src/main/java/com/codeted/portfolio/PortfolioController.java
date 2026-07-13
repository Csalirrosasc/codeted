package com.codeted.portfolio;

import com.codeted.common.ApiResponse;
import com.codeted.portfolio.dto.CreatePortfolioItemRequest;
import com.codeted.portfolio.dto.PortfolioItemResponse;
import com.codeted.portfolio.dto.UpdatePortfolioItemRequest;
import com.codeted.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    @Operation(summary = "List public portfolio items", description = "Returns portfolio or case study items shown on the public site.")
    public ApiResponse<List<PortfolioItemResponse>> findPublicItems() {
        return ApiResponse.success("Portafolio obtenido correctamente", portfolioService.findPublicItems());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EDITOR')")
    @Operation(summary = "List portfolio items for admin", description = "Returns every portfolio item for dashboard administration.")
    public ApiResponse<List<PortfolioItemResponse>> findAll() {
        return ApiResponse.success("Portafolio administrativo obtenido correctamente", portfolioService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EDITOR')")
    @Operation(summary = "Create portfolio item", description = "Creates a new public portfolio entry.")
    public ApiResponse<PortfolioItemResponse> create(@Valid @RequestBody CreatePortfolioItemRequest request) {
        return ApiResponse.success("Caso de portafolio creado correctamente", portfolioService.create(request));
    }

    @PatchMapping("/{publicId}")
    @PreAuthorize("hasAuthority('portfolio.write')")
    public ApiResponse<PortfolioItemResponse> update(@PathVariable UUID publicId, @Valid @RequestBody UpdatePortfolioItemRequest request) {
        return ApiResponse.success("Caso de portafolio actualizado correctamente", portfolioService.update(publicId, request));
    }

    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('portfolio.write')")
    public ApiResponse<Void> delete(@PathVariable UUID publicId) {
        portfolioService.delete(publicId);
        return ApiResponse.success("Caso de portafolio eliminado correctamente");
    }
}
