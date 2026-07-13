package com.codeted.quote.service;

import com.codeted.crm.entity.Customer;
import com.codeted.crm.repository.CustomerRepository;
import com.codeted.quote.dto.CreateQuoteRequest;
import com.codeted.quote.dto.QuoteResponse;
import com.codeted.quote.dto.UpdateQuoteRequest;
import com.codeted.quote.entity.Quote;
import com.codeted.quote.mapper.QuoteMapper;
import com.codeted.quote.repository.QuoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final CustomerRepository customerRepository;
    private final QuoteMapper quoteMapper;

    @Override
    public List<QuoteResponse> findAll() {
        return quoteRepository.findAll().stream().map(quoteMapper::toResponse).toList();
    }

    @Override
    public QuoteResponse create(CreateQuoteRequest request) {
        Customer customer = customerRepository.findByPublicId(request.customerPublicId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        Quote quote = new Quote();
        applyChanges(quote, customer, request.title(), request.description(), request.status(), request.totalAmount());

        return quoteMapper.toResponse(quoteRepository.save(quote));
    }

    @Override
    public QuoteResponse update(UUID publicId, UpdateQuoteRequest request) {
        Quote quote = quoteRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Cotizacion no encontrada"));
        Customer customer = customerRepository.findByPublicId(request.customerPublicId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        applyChanges(quote, customer, request.title(), request.description(), request.status(), request.totalAmount());
        return quoteMapper.toResponse(quoteRepository.save(quote));
    }

    @Override
    public void delete(UUID publicId) {
        Quote quote = quoteRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Cotizacion no encontrada"));
        quoteRepository.delete(quote);
    }

    private void applyChanges(Quote quote, Customer customer, String title, String description, String status, java.math.BigDecimal totalAmount) {
        quote.setCustomer(customer);
        quote.setTitle(title);
        quote.setDescription(description);
        quote.setStatus(status);
        quote.setTotalAmount(totalAmount);
    }
}
