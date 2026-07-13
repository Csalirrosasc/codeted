package com.codeted.crm.mapper;

import com.codeted.crm.dto.CustomerResponse;
import com.codeted.crm.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getPublicId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCompany(),
                customer.getStatus(),
                customer.getNotes()
        );
    }
}
