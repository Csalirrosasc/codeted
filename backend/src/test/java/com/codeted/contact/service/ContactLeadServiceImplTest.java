package com.codeted.contact.service;

import com.codeted.contact.dto.ContactLeadResponse;
import com.codeted.contact.dto.CreateContactLeadRequest;
import com.codeted.contact.dto.UpdateContactLeadStatusRequest;
import com.codeted.contact.entity.ContactLead;
import com.codeted.contact.mapper.ContactLeadMapper;
import com.codeted.contact.repository.ContactLeadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactLeadServiceImplTest {

    @Mock
    private ContactLeadRepository contactLeadRepository;

    @Mock
    private ContactLeadMapper contactLeadMapper;

    @InjectMocks
    private ContactLeadServiceImpl contactLeadService;

    private ContactLead contactLead;

    @BeforeEach
    void setUp() {
        contactLead = new ContactLead();
        contactLead.setStatus("new");
    }

    @Test
    void createShouldPersistLeadWithNewStatus() {
        CreateContactLeadRequest request = new CreateContactLeadRequest(
                "Cesar",
                "cesar@example.com",
                "999999999",
                "CodeTED",
                "Necesito una propuesta",
                "website"
        );

        when(contactLeadRepository.save(any(ContactLead.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contactLeadMapper.toResponse(any(ContactLead.class))).thenReturn(
                new ContactLeadResponse(1L, UUID.randomUUID(), "Cesar", "cesar@example.com", "999999999", "CodeTED", "Necesito una propuesta", "website", "new")
        );

        ContactLeadResponse response = contactLeadService.create(request);

        ArgumentCaptor<ContactLead> captor = ArgumentCaptor.forClass(ContactLead.class);
        verify(contactLeadRepository).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo("new");
        assertThat(response.status()).isEqualTo("new");
    }

    @Test
    void updateStatusShouldChangeLeadStatus() {
        UUID publicId = UUID.randomUUID();
        contactLead.setPublicId(publicId);

        when(contactLeadRepository.findByPublicId(publicId)).thenReturn(Optional.of(contactLead));
        when(contactLeadRepository.save(contactLead)).thenReturn(contactLead);
        when(contactLeadMapper.toResponse(contactLead)).thenReturn(
                new ContactLeadResponse(1L, publicId, "Cesar", "cesar@example.com", null, null, "Hola", "website", "qualified")
        );

        ContactLeadResponse response = contactLeadService.updateStatus(publicId, new UpdateContactLeadStatusRequest("qualified"));

        assertThat(contactLead.getStatus()).isEqualTo("qualified");
        assertThat(response.status()).isEqualTo("qualified");
    }
}
