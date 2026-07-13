package com.codeted.servicecatalog;

import com.codeted.common.ApiResponse;
import com.codeted.servicecatalog.dto.CreateServiceOfferingRequest;
import com.codeted.servicecatalog.dto.ServiceOfferingResponse;
import com.codeted.servicecatalog.dto.UpdateServiceOfferingRequest;
import com.codeted.servicecatalog.service.ServiceOfferingService;
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
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping
    @Operation(summary = "List public services", description = "Returns the commercial services shown on the landing page.")
    public ApiResponse<List<ServiceOfferingResponse>> findPublicServices() {
        return ApiResponse.success("Servicios obtenidos correctamente", serviceOfferingService.findPublicServices());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EDITOR')")
    @Operation(summary = "List services for admin", description = "Returns every service offering for dashboard administration.")
    public ApiResponse<List<ServiceOfferingResponse>> findAll() {
        return ApiResponse.success("Servicios administrativos obtenidos correctamente", serviceOfferingService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EDITOR')")
    @Operation(summary = "Create service offering", description = "Creates a new service offering in the commercial catalog.")
    public ApiResponse<ServiceOfferingResponse> create(@Valid @RequestBody CreateServiceOfferingRequest request) {
        return ApiResponse.success("Servicio creado correctamente", serviceOfferingService.create(request));
    }

    @PatchMapping("/{publicId}")
    @PreAuthorize("hasAuthority('services.write')")
    public ApiResponse<ServiceOfferingResponse> update(@PathVariable UUID publicId, @Valid @RequestBody UpdateServiceOfferingRequest request) {
        return ApiResponse.success("Servicio actualizado correctamente", serviceOfferingService.update(publicId, request));
    }

    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('services.write')")
    public ApiResponse<Void> delete(@PathVariable UUID publicId) {
        serviceOfferingService.delete(publicId);
        return ApiResponse.success("Servicio eliminado correctamente");
    }
}
