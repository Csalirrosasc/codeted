package com.codeted.quote;

import com.codeted.common.ApiResponse;
import com.codeted.quote.dto.CreateQuoteRequest;
import com.codeted.quote.dto.QuoteResponse;
import com.codeted.quote.dto.UpdateQuoteRequest;
import com.codeted.quote.service.QuoteService;
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
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping
    @PreAuthorize("hasAuthority('quotes.read')")
    @Operation(summary = "List quotes", description = "Returns the current commercial quotes.")
    public ApiResponse<List<QuoteResponse>> findAll() {
        return ApiResponse.success("Cotizaciones obtenidas correctamente", quoteService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('quotes.write')")
    @Operation(summary = "Create quote", description = "Creates a new quote linked to a customer.")
    public ApiResponse<QuoteResponse> create(@Valid @RequestBody CreateQuoteRequest request) {
        return ApiResponse.success("Cotizacion creada correctamente", quoteService.create(request));
    }

    @PatchMapping("/{publicId}")
    @PreAuthorize("hasAuthority('quotes.write')")
    public ApiResponse<QuoteResponse> update(@PathVariable UUID publicId, @Valid @RequestBody UpdateQuoteRequest request) {
        return ApiResponse.success("Cotizacion actualizada correctamente", quoteService.update(publicId, request));
    }

    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasAuthority('quotes.write')")
    public ApiResponse<Void> delete(@PathVariable UUID publicId) {
        quoteService.delete(publicId);
        return ApiResponse.success("Cotizacion eliminada correctamente");
    }
}
