package com.challenge.java.service.impl;

import com.challenge.java.exception.InvalidAgeException;
import com.challenge.java.model.Client;
import com.challenge.java.model.DTO.ClientDTO;
import com.challenge.java.model.DTO.ClientWithLifeExpectancyDTO;
import com.challenge.java.repository.ClientRepository;
import com.challenge.java.service.ClientService;
import com.challenge.java.utils.TestClientFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static com.challenge.java.utils.TestClientFactory.listOfClients;
import static com.challenge.java.utils.TestClientFactory.listOfClientsWithLifeExpectancy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    private Client defaultClient;
    private ClientDTO invalidAgeClientDTO;
    private ClientDTO clientDTO;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientService clientService;
    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @BeforeEach
    public void setUp() {
        defaultClient = TestClientFactory.createDefaultClient();
        clientDTO = TestClientFactory.createClientDTOWithCustomValues("John", "Doe", 30, LocalDate.of(1994,1,6));
        invalidAgeClientDTO = TestClientFactory.createClientDTOWithCustomValues("John Exception","Doe" , -3 , LocalDate.of(1994,1,6));
    }

    @Test
    void createClientSuccess() throws Exception {
        when(clientRepository.save(any(Client.class))).thenReturn(defaultClient);

        ClientDTO result = clientServiceImpl.createClient(clientDTO);

        assertEquals(clientDTO.getAge(), result.getAge());
        assertEquals(clientDTO.getBirthDate(), result.getBirthDate());
        assertEquals(clientDTO.getName(), result.getName());
        assertEquals(clientDTO.getLastName(), result.getLastName());
    }

    @Test
    void createClientException() {
        assertThrows(InvalidAgeException.class, () -> clientServiceImpl.createClient(invalidAgeClientDTO));
    }

    @Test
    void getClientsWithLifeExpectancy() {
        when(clientRepository.findAll(any(Pageable.class)))
                .thenReturn(listOfClients());
        Page <ClientWithLifeExpectancyDTO> page = clientServiceImpl.getClientsWithLifeExpectancy(PageRequest.of(0,10));
        for(int i = 0; i < page.getSize(); i++){
            ClientWithLifeExpectancyDTO clientWithLifeExpectancyDTO = page.getContent().get(i);
            assertEquals(clientWithLifeExpectancyDTO.getLifeExpectancy(),listOfClientsWithLifeExpectancy().get(i).getLifeExpectancy());
            assertEquals(clientWithLifeExpectancyDTO.getClientDTO().getAge(),listOfClientsWithLifeExpectancy().get(i).getClientDTO().getAge());
            assertEquals(clientWithLifeExpectancyDTO.getClientDTO().getName(),listOfClientsWithLifeExpectancy().get(i).getClientDTO().getName());
            assertEquals(clientWithLifeExpectancyDTO.getClientDTO().getLastName(),listOfClientsWithLifeExpectancy().get(i).getClientDTO().getLastName());
            assertEquals(clientWithLifeExpectancyDTO.getClientDTO().getBirthDate(),listOfClientsWithLifeExpectancy().get(i).getClientDTO().getBirthDate());
        }
    }
}