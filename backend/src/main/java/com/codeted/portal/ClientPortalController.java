package com.codeted.portal;

import com.codeted.common.ApiResponse;
import com.codeted.portal.dto.ClientPortalResponse;
import com.codeted.portal.service.ClientPortalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-portal")
@RequiredArgsConstructor
public class ClientPortalController {

    private final ClientPortalService clientPortalService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('portal.read.own')")
    @Operation(summary = "Get current client portal", description = "Returns the current client's own projects and quotes.")
    public ApiResponse<ClientPortalResponse> getCurrentPortal() {
        return ApiResponse.success("Portal obtenido correctamente", clientPortalService.getCurrentPortal());
    }
}
