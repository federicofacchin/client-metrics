package com.challenge.java.utils;

import com.challenge.java.model.Client;
import com.challenge.java.model.DTO.ClientDTO;
import com.challenge.java.model.DTO.ClientWithLifeExpectancyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestClientFactory {

    public static Client createDefaultClient() {
        Client client = new Client();
        client.setId(String.valueOf(UUID.randomUUID()));
        client.setName("John");
        client.setLastName("Doe");
        client.setAge(30);
        client.setBirthDate(LocalDate.of(1994, 1, 6));
//        client.setVersion(0);
        return client;
    }

    public static Client createClientWithCustomValues(String name, String lastName,  int age, LocalDate birthDate) {
        Client client = new Client();
        client.setId(String.valueOf(UUID.randomUUID()));
        client.setName(name);
        client.setLastName(lastName);
        client.setAge(age);
        client.setBirthDate(birthDate);
        return client;
    }

    public static ClientDTO createClientDTOWithCustomValues(String name, String lastName, int age, LocalDate birthDate) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(name);
        clientDTO.setLastName(lastName);
        clientDTO.setAge(age);
        clientDTO.setBirthDate(birthDate);
        return clientDTO;
    }

    public static Page<Client> listOfClients() {
        List<Client> clients = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,10);
        for (int i = 0; i < 10; i++) {
            clients.add(createDefaultClient());
        }
        return new PageImpl<>(clients.subList(0, 10), pageable, clients.size());
    }

    public static List<ClientWithLifeExpectancyDTO> listOfClientsWithLifeExpectancy() {
        return listOfClients().getContent().stream().map(client -> {
          return ClientWithLifeExpectancyDTO.builder().lifeExpectancy(47.5)
                  .clientDTO(createClientDTOWithCustomValues("John", "Doe", 30,
                          LocalDate.of(1994,1,6)))
                  .build();

          }
        ).toList();
    }

}
