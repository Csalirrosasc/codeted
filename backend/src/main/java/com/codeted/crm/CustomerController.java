package com.codeted.crm;

import com.codeted.common.ApiResponse;
import com.codeted.crm.dto.CreateCustomerRequest;
import com.codeted.crm.dto.CustomerResponse;
import com.codeted.crm.dto.UpdateCustomerRequest;
import com.codeted.crm.service.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAuthority('customers.read')")
    @Operation(summary = "List customers", description = "Returns the current CRM customers.")
    public ApiResponse<List<CustomerResponse>> findAll() {
        return ApiResponse.success("Clientes obtenidos correctamente", customerService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('customers.write')")
    @Operation(summary = "Create customer", description = "Creates a new customer in the CRM module.")
    public ApiResponse<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request) {
        return ApiResponse.success("Cliente creado correctamente", customerService.create(request));
    }

    @PatchMapping("/{publicId}")
    @PreAuthorize("hasAuthority('customers.write')")
    public ApiResponse<CustomerResponse> update(@PathVariable UUID publicId, @Valid @RequestBody UpdateCustomerRequest request) {
        return ApiResponse.success("Cliente actualizado correctamente", customerService.update(publicId, request));
    }

    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('customers.write')")
    public ApiResponse<Void> delete(@PathVariable UUID publicId) {
        customerService.delete(publicId);
        return ApiResponse.success("Cliente eliminado correctamente");
    }
}
