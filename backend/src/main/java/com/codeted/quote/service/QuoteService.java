package com.codeted.quote.service;

import com.codeted.quote.dto.CreateQuoteRequest;
import com.codeted.quote.dto.QuoteResponse;
import com.codeted.quote.dto.UpdateQuoteRequest;

import java.util.List;
import java.util.UUID;

public interface QuoteService {

    List<QuoteResponse> findAll();

    QuoteResponse create(CreateQuoteRequest request);

    QuoteResponse update(UUID publicId, UpdateQuoteRequest request);

    void delete(UUID publicId);
}
