package com.codeted.quote.mapper;

import com.codeted.quote.dto.QuoteResponse;
import com.codeted.quote.entity.Quote;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapper {

    public QuoteResponse toResponse(Quote quote) {
        return new QuoteResponse(
                quote.getId(),
                quote.getPublicId(),
                quote.getCustomer().getPublicId(),
                quote.getCustomer().getFullName(),
                quote.getTitle(),
                quote.getDescription(),
                quote.getStatus(),
                quote.getTotalAmount()
        );
    }
}
