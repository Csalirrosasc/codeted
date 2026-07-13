package com.codeted.servicecatalog.service;

import com.codeted.servicecatalog.dto.CreateServiceOfferingRequest;
import com.codeted.servicecatalog.dto.ServiceOfferingResponse;
import com.codeted.servicecatalog.dto.UpdateServiceOfferingRequest;
import com.codeted.servicecatalog.entity.ServiceOffering;
import com.codeted.servicecatalog.mapper.ServiceOfferingMapper;
import com.codeted.servicecatalog.repository.ServiceOfferingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ServiceOfferingMapper serviceOfferingMapper;

    @Override
    public List<ServiceOfferingResponse> findPublicServices() {
        return serviceOfferingRepository.findByActiveTrueOrderByFeaturedDescTitleAsc()
                .stream()
                .map(serviceOfferingMapper::toResponse)
                .toList();
    }

    @Override
    public List<ServiceOfferingResponse> findAll() {
        return serviceOfferingRepository.findAll().stream()
                .sorted(Comparator.comparing(ServiceOffering::isFeatured).reversed()
                        .thenComparing(ServiceOffering::getTitle))
                .map(serviceOfferingMapper::toResponse)
                .toList();
    }

    @Override
    public ServiceOfferingResponse create(CreateServiceOfferingRequest request) {
        ServiceOffering serviceOffering = new ServiceOffering();
        applyChanges(serviceOffering, request.title(), request.slug(), request.summary(), request.description(), request.featured(), request.active());
        return serviceOfferingMapper.toResponse(serviceOfferingRepository.save(serviceOffering));
    }

    @Override
    public ServiceOfferingResponse update(UUID publicId, UpdateServiceOfferingRequest request) {
        ServiceOffering serviceOffering = serviceOfferingRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));
        applyChanges(serviceOffering, request.title(), request.slug(), request.summary(), request.description(), request.featured(), request.active());
        return serviceOfferingMapper.toResponse(serviceOfferingRepository.save(serviceOffering));
    }

    @Override
    public void delete(UUID publicId) {
        ServiceOffering serviceOffering = serviceOfferingRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));
        serviceOfferingRepository.delete(serviceOffering);
    }

    private void applyChanges(ServiceOffering serviceOffering, String title, String slug, String summary, String description, boolean featured, boolean active) {
        serviceOffering.setTitle(title);
        serviceOffering.setSlug(slug);
        serviceOffering.setSummary(summary);
        serviceOffering.setDescription(description);
        serviceOffering.setFeatured(featured);
        serviceOffering.setActive(active);
    }
}
