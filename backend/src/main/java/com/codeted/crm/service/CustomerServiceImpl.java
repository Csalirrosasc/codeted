package com.codeted.crm.service;

import com.codeted.crm.dto.CreateCustomerRequest;
import com.codeted.crm.dto.CustomerResponse;
import com.codeted.crm.dto.UpdateCustomerRequest;
import com.codeted.crm.entity.Customer;
import com.codeted.crm.mapper.CustomerMapper;
import com.codeted.crm.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream().map(customerMapper::toResponse).toList();
    }

    @Override
    public CustomerResponse create(CreateCustomerRequest request) {
        Customer customer = new Customer();
        applyChanges(customer, request.fullName(), request.email(), request.phone(), request.company(), request.status(), request.notes());
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse update(UUID publicId, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        applyChanges(customer, request.fullName(), request.email(), request.phone(), request.company(), request.status(), request.notes());
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void delete(UUID publicId) {
        Customer customer = customerRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        customerRepository.delete(customer);
    }

    private void applyChanges(Customer customer, String fullName, String email, String phone, String company, String status, String notes) {
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setCompany(company);
        customer.setStatus(status);
        customer.setNotes(notes);
    }
}
