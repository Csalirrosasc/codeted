package com.codeted.contact.service;

import com.codeted.contact.dto.ContactLeadResponse;
import com.codeted.contact.dto.CreateContactLeadRequest;
import com.codeted.contact.dto.UpdateContactLeadStatusRequest;

import java.util.List;
import java.util.UUID;

public interface ContactLeadService {

    ContactLeadResponse create(CreateContactLeadRequest request);

    List<ContactLeadResponse> findAll();

    ContactLeadResponse updateStatus(UUID publicId, UpdateContactLeadStatusRequest request);

    void delete(UUID publicId);
}
