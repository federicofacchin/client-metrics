package com.challenge.java.service;

import com.challenge.java.model.DTO.ClientDTO;
import com.challenge.java.model.DTO.ClientWithLifeExpectancyDTO;
import com.challenge.java.model.DTO.MetricsResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    MetricsResponseDTO calculateMetrics();

    Page<ClientWithLifeExpectancyDTO> getClientsWithLifeExpectancy(Pageable pageable);
}
