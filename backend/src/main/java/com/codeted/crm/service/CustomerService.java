package com.codeted.crm.service;

import com.codeted.crm.dto.CreateCustomerRequest;
import com.codeted.crm.dto.CustomerResponse;
import com.codeted.crm.dto.UpdateCustomerRequest;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponse> findAll();

    CustomerResponse create(CreateCustomerRequest request);

    CustomerResponse update(UUID publicId, UpdateCustomerRequest request);

    void delete(UUID publicId);
}
