package com.codeted.portal.service;

import com.codeted.auth.entity.User;
import com.codeted.auth.repository.UserRepository;
import com.codeted.crm.entity.Customer;
import com.codeted.portal.dto.ClientPortalResponse;
import com.codeted.project.mapper.ProjectMapper;
import com.codeted.project.repository.ProjectRepository;
import com.codeted.quote.mapper.QuoteMapper;
import com.codeted.quote.repository.QuoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientPortalServiceImpl implements ClientPortalService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final QuoteRepository quoteRepository;
    private final ProjectMapper projectMapper;
    private final QuoteMapper quoteMapper;

    @Override
    public ClientPortalResponse getCurrentPortal() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Customer customer = user.getCustomer();

        if (customer == null) {
            throw new EntityNotFoundException("El usuario no tiene cliente asociado");
        }

        return new ClientPortalResponse(
                customer.getFullName(),
                customer.getEmail(),
                customer.getCompany(),
                projectRepository.findAllByCustomerId(customer.getId()).stream().map(projectMapper::toResponse).toList(),
                quoteRepository.findAllByCustomerId(customer.getId()).stream().map(quoteMapper::toResponse).toList()
        );
    }
}
