package com.codeted.contact.service;

import com.codeted.contact.dto.ContactLeadResponse;
import com.codeted.contact.dto.CreateContactLeadRequest;
import com.codeted.contact.dto.UpdateContactLeadStatusRequest;
import com.codeted.contact.entity.ContactLead;
import com.codeted.contact.mapper.ContactLeadMapper;
import com.codeted.contact.repository.ContactLeadRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactLeadServiceImpl implements ContactLeadService {

    private final ContactLeadRepository contactLeadRepository;
    private final ContactLeadMapper contactLeadMapper;

    @Override
    @Transactional
    public ContactLeadResponse create(CreateContactLeadRequest request) {
        ContactLead contactLead = new ContactLead();
        contactLead.setFullName(request.fullName());
        contactLead.setEmail(request.email());
        contactLead.setPhone(request.phone());
        contactLead.setCompany(request.company());
        contactLead.setMessage(request.message());
        contactLead.setSource(request.source());
        contactLead.setStatus("new");
        return contactLeadMapper.toResponse(contactLeadRepository.save(contactLead));
    }

    @Override
    public List<ContactLeadResponse> findAll() {
        return contactLeadRepository.findAll().stream().map(contactLeadMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ContactLeadResponse updateStatus(UUID publicId, UpdateContactLeadStatusRequest request) {
        ContactLead contactLead = contactLeadRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Lead no encontrado"));
        contactLead.setStatus(request.status());
        return contactLeadMapper.toResponse(contactLeadRepository.save(contactLead));
    }

    @Override
    @Transactional
    public void delete(UUID publicId) {
        ContactLead contactLead = contactLeadRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Lead no encontrado"));
        contactLeadRepository.delete(contactLead);
    }
}
