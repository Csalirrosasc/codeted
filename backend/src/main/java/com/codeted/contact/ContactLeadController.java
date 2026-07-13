package com.codeted.contact;

import com.codeted.common.ApiResponse;
import com.codeted.contact.dto.ContactLeadResponse;
import com.codeted.contact.dto.CreateContactLeadRequest;
import com.codeted.contact.dto.UpdateContactLeadStatusRequest;
import com.codeted.contact.service.ContactLeadService;
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
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactLeadController {

    private final ContactLeadService contactLeadService;

    @PostMapping("/requests")
    @Operation(summary = "Create public contact request", description = "Registers a lead from the public website.")
    public ApiResponse<ContactLeadResponse> create(@Valid @RequestBody CreateContactLeadRequest request) {
        return ApiResponse.success("Solicitud registrada correctamente", contactLeadService.create(request));
    }

    @GetMapping("/leads")
    @PreAuthorize("hasAuthority('leads.read')")
    @Operation(summary = "List leads", description = "Returns administrative contact leads.")
    public ApiResponse<List<ContactLeadResponse>> findAll() {
        return ApiResponse.success("Leads obtenidos correctamente", contactLeadService.findAll());
    }

    @PatchMapping("/leads/{publicId}/status")
    @PreAuthorize("hasAuthority('leads.write')")
    @Operation(summary = "Update lead status", description = "Updates the operational status of a lead.")
    public ApiResponse<ContactLeadResponse> updateStatus(
            @PathVariable UUID publicId,
            @Valid @RequestBody UpdateContactLeadStatusRequest request
    ) {
        return ApiResponse.success("Lead actualizado correctamente", contactLeadService.updateStatus(publicId, request));
    }

    @DeleteMapping("/leads/{publicId}")
    @PreAuthorize("hasAuthority('leads.write')")
    @Operation(summary = "Delete lead", description = "Deletes a lead from the administrative backlog.")
    public ApiResponse<Void> delete(@PathVariable UUID publicId) {
        contactLeadService.delete(publicId);
        return ApiResponse.success("Lead eliminado correctamente");
    }
}
