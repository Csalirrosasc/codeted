package com.codeted.contact.mapper;

import com.codeted.contact.dto.ContactLeadResponse;
import com.codeted.contact.entity.ContactLead;
import org.springframework.stereotype.Component;

@Component
public class ContactLeadMapper {

    public ContactLeadResponse toResponse(ContactLead contactLead) {
        return new ContactLeadResponse(
                contactLead.getId(),
                contactLead.getPublicId(),
                contactLead.getFullName(),
                contactLead.getEmail(),
                contactLead.getPhone(),
                contactLead.getCompany(),
                contactLead.getMessage(),
                contactLead.getSource(),
                contactLead.getStatus()
        );
    }
}
