package com.codeted.servicecatalog.service;

import com.codeted.servicecatalog.dto.CreateServiceOfferingRequest;
import com.codeted.servicecatalog.dto.ServiceOfferingResponse;
import com.codeted.servicecatalog.dto.UpdateServiceOfferingRequest;

import java.util.List;
import java.util.UUID;

public interface ServiceOfferingService {

    List<ServiceOfferingResponse> findPublicServices();

    List<ServiceOfferingResponse> findAll();

    ServiceOfferingResponse create(CreateServiceOfferingRequest request);

    ServiceOfferingResponse update(UUID publicId, UpdateServiceOfferingRequest request);

    void delete(UUID publicId);
}
