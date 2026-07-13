package com.codeted.servicecatalog.mapper;

import com.codeted.servicecatalog.dto.ServiceOfferingResponse;
import com.codeted.servicecatalog.entity.ServiceOffering;
import org.springframework.stereotype.Component;

@Component
public class ServiceOfferingMapper {

    public ServiceOfferingResponse toResponse(ServiceOffering serviceOffering) {
        return new ServiceOfferingResponse(
                serviceOffering.getId(),
                serviceOffering.getPublicId(),
                serviceOffering.getTitle(),
                serviceOffering.getSlug(),
                serviceOffering.getSummary(),
                serviceOffering.getDescription(),
                serviceOffering.isFeatured(),
                serviceOffering.isActive()
        );
    }
}
